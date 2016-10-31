package com.slickqa.client.simple.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.Result;
import com.slickqa.client.simple.definitions.SlickFile;
import com.slickqa.client.simple.definitions.SlickLog;
import com.slickqa.client.simple.definitions.TestRun;
import com.slickqa.client.simple.utils.JsonUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleClientImpl implements SlickSimpleClient {
    private final WebTarget target;

    public SlickSimpleClientImpl(String baseUrl, Client restClient) {
        this.target = restClient.target(baseUrl);
    }

    @Override
    public TestRun createTestRun(TestRun testRun) {
        target.path("/api/simple/create_test_run/");
        Entity entityData = Entity.entity(testRun, MediaType.APPLICATION_JSON);
        Response response = target.request(MediaType.APPLICATION_JSON).post(entityData);
        TestRun result = response.readEntity(TestRun.class);
        return result;
    }
//
//    @Override
//    public ArrayList<Result> sendResults(String testRunId, ArrayList<Result> results) {
//        return null;
//    }
//
//    @Override
//    public void sendLog(String resultId, SlickLog slickLog) {
//        ObjectMapper mapper = JsonUtil.getObjectMapper();
//        ArrayNode entry = mapper.createArrayNode();
//        entry.add("class LogEntry {\n");
//        entry.add("  exceptionClassName: " );
//
//
////            StringBuilder sb = new StringBuilder();
////            sb.append("class LogEntry {\n");
////            sb.append("  exceptionClassName: ").append(exceptionClassName).append("\n");
////            sb.append("  level: ").append(level).append("\n");
////            sb.append("  exceptionMessage: ").append(exceptionMessage).append("\n");
////            sb.append("  entryTime: ").append(entryTime).append("\n");
////            sb.append("  loggerName: ").append(loggerName).append("\n");
////            sb.append("  exceptionStackTrace: ").append(exceptionStackTrace).append("\n");
////            sb.append("  message: ").append(message).append("\n");
////            sb.append("}\n");
//
//    }
//
//    @Override
//    public void sendFile(String resultId, SlickFile slickFile) {
//        String serverFileName = SlickSimpleWeb.getFileId();
//        // Get Mime type from file
//        // Split file into chunks of BLAH
//        // Create the file on the server
//        // Upload the file chunks to the file
//
//        //    sb.append("  mimetype: ").append(mimetype).append("\n");
////    sb.append("  chunkSize: ").append(chunkSize).append("\n");
////    sb.append("  filename: ").append(filename).append("\n");
////    sb.append("  length: ").append(length).append("\n");
////    sb.append("  uploadDate: ").append(uploadDate).append("\n");
////    sb.append("  id: ").append(id).append("\n");
////    sb.append("  md5: ").append(md5).append("\n")

    }
}
