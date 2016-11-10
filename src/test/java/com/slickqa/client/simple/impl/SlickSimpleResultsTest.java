package com.slickqa.client.simple.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.SlickSimpleTestUtils;
import com.slickqa.client.simple.definitions.*;
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
import java.io.IOException;
import java.util.ArrayList;

import static com.slickqa.client.simple.utils.JsonUtil.mapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Keith on 11/8/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleResultsTest {
    @Injectable
    Client restClient;

    @Injectable
    WebTarget targetOne;

    @Injectable
    WebTarget targetTwo;

    @Injectable
    WebTarget targetThree;

    @Injectable
    WebTarget targetFour;

    @Injectable
    Invocation.Builder targetBuilderOne;

    @Injectable
    Invocation.Builder targetBuilderTwo;

    String testRunId = "abcd";
    final String mediaType = MediaType.APPLICATION_JSON;
    final String baseUrl = "http://localhost/slick";
    final String path = "/api/simple/" + testRunId + "/results";

    @Test
    public void addResultsCreate() throws IOException {
        ArrayList<SlickResult> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(SlickSimpleTestUtils.getResult("blah" + i, null));
        }

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickResult result : results) {
            mainArray.add(result.toObjectNode());
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
        ArrayList<SlickResult> slickResults = simpleClient.addResults(testRunId, results);

        for (int i = 0; i < results.size(); i++) {
            SlickSimpleTestUtils.validateResult(results.get(i), slickResults.get(i), false);
        }
    }

    @Test
    public void addResultsUpdate() throws IOException {
        ArrayList<SlickResult> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(SlickSimpleTestUtils.getResult("blah" + i, "id" + i));
        }

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickResult result : results) {
            mainArray.add(result.toObjectNode());
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

            targetBuilderOne.put((Entity) any);
            result = response;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        ArrayList<SlickResult> slickResults = simpleClient.addResults(testRunId, results);

        for (int i = 0; i < results.size(); i++) {
            SlickSimpleTestUtils.validateResult(results.get(i), slickResults.get(i), true);
        }
    }

    @Test
    public void addResultsBoth() throws IOException {
        ArrayList<SlickResult> results = new ArrayList<>();
        ArrayNode createArray = mapper.createArrayNode();
        ArrayNode updateArray = mapper.createArrayNode();

        for (int i = 0; i < 5; i++) {
            SlickResult result = SlickSimpleTestUtils.getResult("blah" + i, null );
            results.add(result);
            createArray.add(result.toObjectNode());
        }

        for (int i = 0; i < 5; i++) {
            SlickResult result = SlickSimpleTestUtils.getResult("blah" + i, "id" + i);
            results.add(result);
            updateArray.add(result.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity createEntityData = Entity.entity(createArray, mediaType);
        Entity updateEntityData = Entity.entity(updateArray, mediaType);

        final Response createResponse = Response.status(200).entity(createEntityData).type(mediaType).build();
        final Response updateResponse = Response.status(200).entity(updateEntityData).type(mediaType).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilderOne;

            targetBuilderOne.post((Entity) any);
            result = createResponse;

            restClient.target(baseUrl);
            result = targetThree;

            targetThree.path(path);
            result = targetFour;

            targetFour.request(mediaType);
            result = targetBuilderTwo;

            targetBuilderTwo.put((Entity) any);
            result = updateResponse;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        ArrayList<SlickResult> slickResults = simpleClient.addResults(testRunId, results);

        assertTrue(slickResults.size() == 10);

    }
}
