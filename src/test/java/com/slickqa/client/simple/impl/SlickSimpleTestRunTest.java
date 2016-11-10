package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.SlickSimpleTestUtils;
import com.slickqa.client.simple.definitions.SlickTestRun;
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
import java.io.IOException;

/**
 * Created by Keith on 11/8/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleTestRunTest {
    @Injectable
    Client restClient;

    @Injectable
    WebTarget targetOne;

    @Injectable
    WebTarget targetTwo;

    @Injectable
    Invocation.Builder targetBuilderOne;

    final String mediaType = MediaType.APPLICATION_JSON;
    final String baseUrl = "http://localhost/slick";
    String path = "/api/simple/testruns";

    @Test
    public void addTestRun() throws IOException {
        SlickTestRun testRun = SlickSimpleTestUtils.getTestRun("Foo", null);

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(testRun.toObjectNode(), mediaType);

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
        SlickTestRun slickTestRun = simpleClient.addTestRun(testRun);
        SlickSimpleTestUtils.validateTestRun(testRun, slickTestRun, false);
    }

    @Test(expected = HTTPException.class)
    public void addTestRunBadStatus() throws IOException {
        SlickTestRun testRun = SlickSimpleTestUtils.getTestRun("Foo", null);

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(testRun.toObjectNode(), mediaType);

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
        simpleClient.addTestRun(testRun);
    }

    @Test(expected = IOException.class)
    public void addTestRunFooBar() throws IOException {
        SlickTestRun testRun = SlickSimpleTestUtils.getTestRun("Foo", null);

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(testRun.toObjectNode(), mediaType);

        final Response response = Response.status(200).entity("foo").type(mediaType).build();

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
        simpleClient.addTestRun(testRun);
    }

}
