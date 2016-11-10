package com.slickqa.client.simple.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.SlickSimpleTestUtils;
import com.slickqa.client.simple.definitions.SlickLog;
import com.slickqa.client.simple.utils.JsonUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import java.util.ArrayList;

import static com.slickqa.client.simple.utils.JsonUtil.mapper;

/**
 * Created by Keith on 11/8/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleLogTest {
    @Injectable
    Client restClient;

    @Injectable
    WebTarget targetOne;

    @Injectable
    WebTarget targetTwo;

    @Injectable
    Invocation.Builder targetBuilderOne;

    String testRunId = "abcd";
    String resultId = "1234";
    final String mediaType = MediaType.APPLICATION_JSON;
    final String baseUrl = "http://localhost/slick";
    final String path = "/api/simple/" + testRunId + "/results/" + resultId + "/log";

    @Test
    public void addLogs() {
        ArrayList<SlickLog> logs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            logs.add(SlickSimpleTestUtils.getSlickLog("blah"));
        }

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickLog log : logs) {
            mainArray.add(log.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(mainArray, mediaType);

        final Response response = Response.status(200).entity(entityData).type(mediaType).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilderOne;

            targetBuilderOne.post((Entity) any);
            result = response;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        simpleClient.addLogs(testRunId, resultId, logs);
    }

    @Test(expected = HTTPException.class)
    public void addLogsBadStatus() {
        ArrayList<SlickLog> logs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            logs.add(SlickSimpleTestUtils.getSlickLog("blah"));
        }

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickLog log : logs) {
            mainArray.add(log.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(mainArray, mediaType);

        final Response response = Response.status(301).entity(entityData).type(mediaType).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilderOne;

            targetBuilderOne.post((Entity) any);
            result = response;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        simpleClient.addLogs(testRunId, resultId, logs);
    }

}
