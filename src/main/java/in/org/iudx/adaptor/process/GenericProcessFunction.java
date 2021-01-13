package in.org.iudx.adaptor.process;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import in.org.iudx.adaptor.datatypes.GenericJsonMessage;
import in.org.iudx.adaptor.codegen.Tagger;
import in.org.iudx.adaptor.codegen.Transformer;
import in.org.iudx.adaptor.source.ApiConfig;

public class GenericProcessFunction 
  extends KeyedProcessFunction<String,GenericJsonMessage,String> {

  /* Something temporary for now */
  private String STATE_NAME = "api state";

  private ValueState<GenericJsonMessage> streamState;

  private ApiConfig<Tagger,Transformer> apiConfig;

  public GenericProcessFunction(ApiConfig<Tagger,Transformer> apiConfig) {
    this.apiConfig = apiConfig;
  }

  @Override
  public void open(Configuration config) {
    ValueStateDescriptor<GenericJsonMessage> stateDescriptor =
      new ValueStateDescriptor<>(STATE_NAME, GenericJsonMessage.class);
    streamState = getRuntimeContext().getState(stateDescriptor);
  }

  @Override
  public void processElement(GenericJsonMessage msg,
                              Context context, Collector<String> out) throws Exception {
    GenericJsonMessage previousMessage = streamState.value();
    /* Update state with current message if not done */
    if (previousMessage == null) {
      streamState.update(msg);
    } else {
      /* Tranformer logic in transform function applied here */
      /* Add deduplication logic here */
      out.collect(apiConfig.transformer.transform(msg.body));
      streamState.clear();
    }
  }

}
