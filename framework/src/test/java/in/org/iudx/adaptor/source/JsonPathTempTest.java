package in.org.iudx.adaptor.source;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;

import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.configuration.Configuration;

import in.org.iudx.adaptor.sink.DumbSink;
import in.org.iudx.adaptor.process.GenericProcessFunction;
import in.org.iudx.adaptor.codegen.SimpleATestParser;
import in.org.iudx.adaptor.codegen.SimpleATestTransformer;
import in.org.iudx.adaptor.source.HttpSource;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
//import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
//import org.apache.flink.streaming.api.operators.StreamFlatMap;

import in.org.iudx.adaptor.codegen.ApiConfig;
import in.org.iudx.adaptor.codegen.Transformer;
import in.org.iudx.adaptor.codegen.Deduplicator;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.DocumentContext;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;
import org.mockito.Mockito;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.*;
import java.util.List;

import org.apache.flink.test.util.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;

import in.org.iudx.adaptor.datatypes.Message;
import in.org.iudx.adaptor.codegen.SimpleBTestParser;
import in.org.iudx.adaptor.codegen.SimpleADeduplicator;

public class JsonPathTempTest {

    @Test
    public void testJSPath() throws Exception {

        while(true) {

            String parseSpecArr = new JSONObject()
                    .put("timestampPath", "$.time" )
                    .put("keyPath", "$.id" )
                    .put("containerPath", "$.data" )
                    .put("inputTimeFormat", "yyyy-MM-dd HH:mm:ss" )
                    .put("outputTimeFormat", "yyyy-MM-dd'T'HH:mm:ssXXX" )
                    .toString();

            String arrayData =
                    new JSONObject().put("data",
                            new JSONArray()
                                    .put(new JSONObject()
                                            .put("time", "2021-04-01 12:00:01" )
                                            .put("id", "123" )
                                            .put("k", 1.5)
                                    )
                                    .put((new JSONObject()
                                            .put("time", "2021-05-01 12:00:01" )
                                            .put("id", "4356" )
                                            .put("k", 2.5)
                                    ))).toString();

            JsonPathParser<List<Message>> parser = new JsonPathParser<List<Message>>(parseSpecArr);

            List<Message> m = parser.parse(arrayData);
            for(int i = 0; i<m.size();i++) {
                System.out.println(m.get(i).toString());
            }

            parseSpecArr = new JSONObject()
                    .put("keyPath", "$.id")
                    .put("trickle", new JSONArray()
                            .put(new JSONObject()
                                    .put("keyPath", "$.time")
                                    .put("keyName", "time")))
                    .put("timestampPath", "$.time")
                    .put("containerPath", "$.data")
                    .put("inputTimeFormat","yyyy-MM-dd HH:mm:ss")
                    .put("outputTimeFormat", "yyyy-MM-dd'T'HH:mm:ssXXX")
                    .toString();

            arrayData =
                    new JSONObject().put("time", "2021-04-01 12:00:01")
                            .put("data",
                                    new JSONArray()
                                            .put(new JSONObject()
                                                    .put("id", "123")
                                                    .put("k", 1.5)
                                            )
                                            .put((new JSONObject()
                                                    .put("id", "4356")
                                                    .put("k", 2.5)
                                            ))).toString();

            parser = new JsonPathParser<List<Message>>(parseSpecArr);

            m = parser.parse(arrayData);

            parseSpecArr = new JSONObject()
                    .put("keyPath", "$.id")
                    .put("containerPath", "$.data")
                    .toString();

            arrayData =
                    new JSONObject().put("data",
                            new JSONArray()
                                    .put(new JSONObject()
                                            .put("time", "2021-04-01 12:00:01")
                                            .put("id", "123")
                                            .put("k", 1.5)
                                    )
                                    .put((new JSONObject()
                                            .put("time", "2021-05-01 12:00:01")
                                            .put("id", "4356")
                                            .put("k", 2.5)
                                    ))).toString();

            parser = new JsonPathParser<List<Message>>(parseSpecArr);

            m = parser.parse(arrayData);

        }


    }

}