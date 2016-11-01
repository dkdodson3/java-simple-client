package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.definitions.SlickIdentity;
import com.slickqa.client.simple.definitions.TestRun;
import com.slickqa.client.simple.definitions.TestRunBuilder;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Keith on 10/31/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleClientTest {
    @Injectable
    Client restClient;

    @Injectable
    WebTarget targetOne;

    @Injectable
    WebTarget targetTwo;

    @Injectable
    Invocation.Builder targetBuilder;

    // With Result

    // With Multiple Results

    // With Test case

    @Test
    public void initializeClientWithoutClient() {
        final String baseUrl = "http://localhost/slick";

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl);
        assertNotNull(simpleClient);
        assertThat(simpleClient, instanceOf(SlickSimpleClientImpl.class));
    }

    @Test
    public void getTestRunValidData() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = "/api/simple/create_test_run";

        String projectName = "foo";
        String projectId = "1234-BOB";

        String testRunName = "bar";
        String testRunId = "1234-FRANK";

        SlickIdentity testProject = new SlickIdentity(projectName, null);
        TestRun testRun = new TestRunBuilder().addProject(testProject).build();


        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        TestRun testRunRetValue = TestRun.builder().addTestRun(testRunRet).addProject(testProjectRet).build();

        final Entity entityDataRet = testRunRetValue.toEntity(mediaType);
        final Response response = Response.status(200).type(mediaType).entity(entityDataRet).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilder;

            targetBuilder.post((Entity) any);
            result = response;
        }};


        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        TestRun returnValue = simpleClient.createTestRun(testRun);
        Assert.assertEquals(testRunRetValue, returnValue);
    }

    @Test
    public void getTestRunBadStatus() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = "/api/simple/create_test_run";

        String projectName = "foo";
        String projectId = "1234-BOB";

        String testRunName = "bar";
        String testRunId = "1234-FRANK";

        SlickIdentity testProject = new SlickIdentity(projectName, null);
        TestRun testRun = TestRun.builder().addProject(testProject).build();

        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        TestRun testRunRetValue = TestRun.builder().addTestRun(testRunRet).addProject(testProjectRet).build();

        final Entity entityDataRet = testRunRetValue.toEntity(mediaType);
        final Response response = Response.status(301).type(mediaType).entity(entityDataRet).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilder;

            targetBuilder.post((Entity) any);
            result = response;
        }};


        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        TestRun returnValue = simpleClient.createTestRun(testRun);
        Assert.assertNull(returnValue);
    }


}
