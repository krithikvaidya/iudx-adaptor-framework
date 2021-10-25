package in.org.iudx.adaptor.utils;

import in.org.iudx.adaptor.datatypes.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

public class MinioClientHelperTest {

    private static final Logger LOGGER = LogManager.getLogger(HashMapStateTest.class);

    private static MinioClientHelper client;

    @BeforeAll
    public static void initialize() {
        LOGGER.debug("Info: Testing Minio Client Helper");

        client = new MinioClientHelper.Builder()
                .bucket("custom-state")
                .endpoint("http://localhost:9000")
                .build();
    }

    @Test
    void testMinioClient() throws Exception {
        LOGGER.info("MinioClient: Testing upload object test");
        String sampleInput = "Data to be added in minio";
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outBytes);
        out.writeObject(sampleInput);
        client.putObject("test_state", outBytes.toByteArray());
        LOGGER.info("MinioClient: Object uploaded successfully");

        LOGGER.info("MinioClient: Testing get object");
        byte[] bytes = client.getObject("test_state");
        ByteArrayInputStream inBytes = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(inBytes);
        LOGGER.info("MinioClient: objected downloaded --" + in.readObject().toString());

        LOGGER.info("MinioClient: Testing remove object");
        client.removeObject("test_state");
        LOGGER.info("MinioClient: Object removed successfully");
    }
}
