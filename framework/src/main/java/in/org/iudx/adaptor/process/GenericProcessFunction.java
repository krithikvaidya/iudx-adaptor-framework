package in.org.iudx.adaptor.process;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import in.org.iudx.adaptor.datatypes.Message;
import in.org.iudx.adaptor.codegen.ApiConfig;
import in.org.iudx.adaptor.codegen.Transformer;
import in.org.iudx.adaptor.codegen.Deduplicator;

/* Primarily used for stateless transformation and deduplication
 * Avoid transforming here if you use a library with heavy initialization*/

public class GenericProcessFunction 
  extends KeyedProcessFunction<String,Message,Message> {

  /* Something temporary for now */
  private String STATE_NAME = "api state";

  private ValueState<Message> streamState;

  private Transformer transformer;
  private Deduplicator deduplicator;

  public static final OutputTag<String> errorStream 
    = new OutputTag<String>("error") {};


  private static final long serialVersionUID = 43L;

  public GenericProcessFunction(Transformer transformer,
      Deduplicator deduplicator) {
    this.transformer = transformer;
    this.deduplicator = deduplicator;
  }

  public GenericProcessFunction(Deduplicator deduplicator) {
    this.transformer = null;
    this.deduplicator = deduplicator;
  }

  @Override
  public void open(Configuration config) {
    ValueStateDescriptor<Message> stateDescriptor =
      new ValueStateDescriptor<>(STATE_NAME, Message.class);
    streamState = getRuntimeContext().getState(stateDescriptor);
  }

  @Override
  public void processElement(Message msg,
      Context context, Collector<Message> out) throws Exception {
    Message previousMessage = streamState.value();
    /* Update state with current message if not done */
    if (previousMessage == null) {
      streamState.update(msg);
    } else {
      if(deduplicator.isDuplicate(previousMessage, msg) == true) {
        return;
      }
    }
    try {
      if (transformer == null) {
        out.collect(msg);
      } else {
        Message transformedMessage = transformer.transform(msg);
        out.collect(transformedMessage);
      }
    } catch (Exception e) {
      /* TODO */
      String tmpl = 
        "{\"streams\": [ { \"stream\": { \"flinkhttp\": \"test-sideoutput\"}, \"values\": [[\"" 
        + Long.toString(System.currentTimeMillis() * 1000000) + "\", \"error\"]]}]}";
      context.output(errorStream, tmpl) ;
    }
    streamState.update(msg);
  }
}
