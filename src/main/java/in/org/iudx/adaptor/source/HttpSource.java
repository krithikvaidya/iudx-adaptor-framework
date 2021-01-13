package in.org.iudx.adaptor.source;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.configuration.Configuration;

import in.org.iudx.adaptor.datatypes.GenericJsonMessage;
import in.org.iudx.adaptor.codegen.Tagger;
import in.org.iudx.adaptor.codegen.Transformer;


/**
 * {@link HttpSource} - The HttpSource Class
 *
 * This extends {@link RichSourceFunction} which implements stateful functionalities.
 * This generic function exchanges meesages as {@link GenericJsonMessage} objects.
 *
 * Notes: 
 *  - ?This is serializable from flink examples
 *  - The constructor can only take a serializable object, {@link ApiConfig<Tagger,Transformer>}
 *
 */
public class HttpSource extends RichSourceFunction <GenericJsonMessage>{

  private static final long serialVersionUID = 1L;
  private volatile boolean running = true;
  private HttpEntity httpEntity;
  private ApiConfig<Tagger,Transformer> apiConfig;

  /**
   * {@link HttpEntity} Constructor
   * 
   * @param ApiConfig<Tagger,Transformer> Api configuration object
   *
   * Note: 
   *   - Only set configuration here. Don't initialize {@link HttpEntity}.
   */
  public HttpSource(ApiConfig<Tagger,Transformer> apiConfig) {
    this.apiConfig = apiConfig;
  }

  /**
   * Retrieve stateful context info
   * 
   * @param Configuration Flink managed state configuration
   *
   * Note: 
   *   - This is where {@link HttpEntity} must be initialized
   */
  @Override
  public void open(Configuration config) throws Exception {
    super.open(config);
    httpEntity = new HttpEntity(apiConfig);
  }

  /**
   * Forever loop with a delay
   * 
   * @param SourceContext ?Context
   *
   * TODOS:
   *  - Is thread.sleep the best way?
   *
   */
  @Override
  public void run(SourceContext<GenericJsonMessage> ctx) throws Exception {
    /** TODO:
     *    - Configure delays
     **/
    while (running) {

      GenericJsonMessage msg = httpEntity.getMessage();
      ctx.collectWithTimestamp(msg, msg.getEventTime());
      ctx.emitWatermark(new Watermark(msg.getEventTime()));

      Thread.sleep(apiConfig.pollingInterval);
    }
  }

  @Override
  public void cancel() {

  }

}
