package com.slickqa.client.simple.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slickqa.client.simple.definitions.SlickIdentity;
import com.slickqa.client.simple.definitions.TestRun;
import com.slickqa.client.simple.utils.JsonUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Keith on 10/31/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleClientTest {
    @Injectable
    Client restClient;

    @Injectable
    WebTarget targetOne;

    String baseUrl;
    SlickSimpleClientImpl simpleClient;

    @Before
    public void setUp() {
        baseUrl = "http://localhost/slick";
        simpleClient = new SlickSimpleClientImpl(baseUrl, restClient);
    }

    @Test
    public void getTestRun() {
        final String projectName = "foo";
        final String projectId = "1234-BOB";

        final String testRunName = "bar";
        final String testRunId = "1234-FRANK";

        final SlickIdentity testProjectInitial = new SlickIdentity(projectName, null);
        final TestRun testRunInitial = TestRun.builder().testRun(testProjectInitial).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            SlickIdentity testProjectIdent = new SlickIdentity(projectName, projectId);
            SlickIdentity testRunIdent = new SlickIdentity(testRunName, testRunId);
            TestRun testRunValue = TestRun.builder().testRun(testRunIdent).project(testProjectIdent).build();
            Entity entityData = Entity.entity(testRunValue, MediaType.APPLICATION_JSON);

            targetOne.request(MediaType.APPLICATION_JSON).post(entityData);
            result = Response.status(200).type(MediaType.APPLICATION_JSON).entity(entityData).build();
        }};

        simpleClient.createTestRun(testRunInitial);
    }


}
