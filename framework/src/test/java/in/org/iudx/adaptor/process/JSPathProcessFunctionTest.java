package in.org.iudx.adaptor.process;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.apache.flink.test.util.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;

import in.org.iudx.adaptor.sink.DumbSink;
import in.org.iudx.adaptor.process.GenericProcessFunction;
import in.org.iudx.adaptor.codegen.SimpleATestParser;
import in.org.iudx.adaptor.codegen.SimpleADeduplicator;
import in.org.iudx.adaptor.codegen.SimpleATestTransformer;
import in.org.iudx.adaptor.codegen.ApiConfig;
import in.org.iudx.adaptor.source.HttpSource;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.logging.log4j.LogManager;
//import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
//import org.apache.flink.streaming.api.operators.StreamFlatMap;
import org.apache.logging.log4j.Logger;

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
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.*;
import java.util.List;

import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.apache.flink.test.util.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;

import in.org.iudx.adaptor.datatypes.Message;
import in.org.iudx.adaptor.sink.DumbSink;
import in.org.iudx.adaptor.process.GenericProcessFunction;
import in.org.iudx.adaptor.codegen.SimpleATestParser;
import in.org.iudx.adaptor.codegen.SimpleBTestParser;
import in.org.iudx.adaptor.codegen.SimpleADeduplicator;
import in.org.iudx.adaptor.codegen.ApiConfig;
import in.org.iudx.adaptor.source.HttpSource;

public class JSPathProcessFunctionTest {

    @Test
    public void testJSPath() throws Exception {

        String pathSpec;
        try {
            pathSpec = new String(
                    Files.readAllBytes(
                            Paths.get(
                                    "src/test/java/in/org/iudx/adaptor/process/pathSpec.json")));

        } catch (Exception e) {
            return;
        }

        JSPathProcessFunction obj = new JSPathProcessFunction(pathSpec.toString());
        Collector<Message> collector = Mockito.mock(Collector.class);

        obj.open(new Configuration());

        String input = "{ \"time\": \"2021-03-11T12:59:20Z\", \"k1\": 769, \"deviceId\": \"abc-123\" }";
        JSONObject data = new JSONObject(input);
        Message msg = new Message();
        Instant time = Instant.parse(data.getString("time"));
        String key = data.getString("deviceId");
        msg.setKey(key);
        msg.setEventTimestamp(time);
        msg.setResponseBody(input);

//        for(int i = 0; i < 10000; i++) {
            obj.flatMap(msg, collector);
//            Thread.sleep(1000);
//            Mockito.verify(collector, Mockito.times(1));
//        }


    }

//    private OneInputStreamOperatorTestHarness<Message, Message> harness;
//
//    public void setupTestHarness() throws Exception {
//        setupHarness();
//    }
//
//    private void setupHarness() throws Exception {
//
//        String pathSpec;
//        try {
//            pathSpec = new String(
//                    Files.readAllBytes(
//                            Paths.get(
//                                    "src/test/java/in/org/iudx/adaptor/process/pathSpec.json")));
//
//        } catch (Exception e) {
//            return;
//        }
//
//        JSPathProcessFunction obj = new JSPathProcessFunction(pathSpec.toString());
//        this.harness = new OneInputStreamOperatorTestHarness<>(new StreamFlatMap<>(obj));
//
//        this.harness.setup();
//        this.harness.open();
//    }
//
//    @Test
//    public void testJSPath() throws Exception {
//
//        //instantiate user-defined function
//
//        Collector<Message> collector = Mockito.mock(Collector.class);
//
//        setupTestHarness();
//
//
////        Method[] methods = OneInputStreamOperatorTestHarness<Message, Message>.class.getDeclaredMethods();
////        int nMethod = 1;
////        System.out.println("1. List of all methods of Person class");
////        for (Method method : methods) {
////            System.out.printf("%d. %s", ++nMethod, method);
////            System.out.println();
////        }
////        System.out.printf("%d. End - all  methods of Person class", ++nMethod);
//
//
//        String input = "{ \"time\": \"2021-03-11T12:59:20Z\", \"k1\": 769, \"deviceId\": \"abc-123\" }";
//        JSONObject data = new JSONObject(input);
//        Message msg = new Message();
//        Instant time = Instant.parse(data.getString("time"));
//        String key = data.getString("deviceId");
//        msg.setKey(key);
//        msg.setEventTimestamp(time);
//        msg.setResponseBody(input);
//
//        this.harness.processElement(msg, 100);
//
//        Mockito.verify(collector, Mockito.times(1));
//    }
}