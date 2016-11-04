package com.slickqa.client.simple.impl;

import com.google.common.collect.Lists;
import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.definitions.*;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import java.util.ArrayList;

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
    WebTarget targetThree;

    @Injectable
    Invocation.Builder targetBuilderOne;

    @Injectable
    Invocation.Builder targetBuilderTwo;

    @Test
    public void initializeClientWithoutClient() {
        final String baseUrl = "http://localhost/slick";

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl);
        assertNotNull(simpleClient);
        assertThat(simpleClient, instanceOf(SlickSimpleClientImpl.class));
    }

    @Test
    public void createTestRun() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = SlickSimpleClientImpl.CREATE_TEST_RUN_PATH;

        String projectName = "foo";
        SlickIdentity testProject = new SlickIdentity(projectName, null);
        SlickTestCase testCaseOne = SlickTestCase.builder()
                .addTestTitle("bar")
                .addAutomationId("foo")
                .addAutomationTool("blah")
                .addSteps(Lists.newArrayList("step"))
                .addExpectations(Lists.newArrayList("expectation"))
                .build();
        SlickIdentity identity = new SlickIdentity("BLAH", "1234");
        SlickResult resultOne = SlickResult.builder().addIdentity(identity).addTestCase(testCaseOne).build();
        SlickTestRun testRun = new SlickTestRunBuilder().addResult(resultOne).addProject(testProject).build();


        String projectId = "1234-BOB";
        String testRunName = "bar";
        String testRunId = "1234-FRANK";
        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addResult(resultOne).addProject(testProjectRet).build();
        final Entity entityDataRet = Entity.entity(testRunRetValue, mediaType);
        final Response response = Response.status(200).type(mediaType).entity(entityDataRet).build();

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
        SlickTestRun returnValue = simpleClient.addTestRun(testRun);
        assertEquals(testRunRetValue, returnValue);
    }

    @Test(expected = HTTPException.class)
    public void createTestRunBadStatus() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = SlickSimpleClientImpl.CREATE_TEST_RUN_PATH;

        String projectName = "foo";
        SlickTestCase testCaseOne = SlickTestCase.builder()
                .addTestTitle("bar")
                .addAutomationId("foo")
                .addAutomationTool("blah")
                .addSteps(Lists.newArrayList("step"))
                .addExpectations(Lists.newArrayList("expectation"))
                .build();
        SlickIdentity identity = new SlickIdentity("BLAH", "1234");
        SlickResult resultOne = SlickResult.builder().addIdentity(identity).addTestCase(testCaseOne).build();
        SlickIdentity testProject = new SlickIdentity(projectName, null);
        SlickTestRun testRun = SlickTestRun.builder().addResult(resultOne).addProject(testProject).build();

        String projectId = "1234-BOB";
        String testRunName = "bar";
        String testRunId = "1234-FRANK";
        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addProject(testProjectRet).addResult(resultOne).build();
        final Entity entityDataRet = Entity.entity(testRunRetValue, mediaType);
        final Response response = Response.status(301).type(mediaType).entity(entityDataRet).build();

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
        try{
            simpleClient.addTestRun(testRun);
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 301);
            throw e;
        }
    }

    @Test
    public void getTestRunValidDataWithResults() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = SlickSimpleClientImpl.CREATE_TEST_RUN_PATH;

        String projectName = "foo";
        SlickTestCase testCaseOne = SlickTestCase.builder()
                .addTestTitle("bar")
                .addAutomationId("foo")
                .addAutomationTool("blah")
                .addSteps(Lists.newArrayList("step"))
                .addExpectations(Lists.newArrayList("expectation"))
                .build();
        SlickIdentity identity = new SlickIdentity("BLAH", "1234");
        SlickResult resultOne = SlickResult.builder().addIdentity(identity).addTestCase(testCaseOne).build();
        SlickIdentity testProject = new SlickIdentity(projectName, null);
        SlickTestRun testRun = new SlickTestRunBuilder().addProject(testProject).addResult(resultOne).build();

        String projectId = "1234-BOB";
        String testRunName = "bar";
        String testRunId = "1234-FRANK";
        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        SlickTestCase testCaseTwo = SlickTestCase.builder()
                .addTestTitle("bar")
                .addAutomationId("foo")
                .addAutomationTool("blah")
                .addSteps(Lists.newArrayList("step"))
                .addExpectations(Lists.newArrayList("expectation"))
                .build();
        SlickIdentity identityTwo = new SlickIdentity("BLAH2", "1235");
        SlickResult resultTwo = SlickResult.builder().addIdentity(identityTwo).addTestCase(testCaseTwo).build();
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addResult(resultTwo).addProject(testProjectRet).build();
        final Entity entityDataRet = Entity.entity(testRunRetValue, mediaType);
        final Response response = Response.status(200).type(mediaType).entity(entityDataRet).build();

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
        SlickTestRun returnValue = simpleClient.addTestRun(testRun);
        assertEquals(testRunRetValue, returnValue);
    }

    @Test(expected = HTTPException.class)
    public void addLogsBadStatus() {
        String resultId = "1234";
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = SlickSimpleClientImpl.LOG_PATH(resultId);
        final Response response = Response.status(301).type(mediaType).build();

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

        SlickLog logs = SlickLog.builder().addResultId(resultId).addLog("foo").build();
        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try{
            simpleClient.addLog(logs);
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 301);
            throw e;
        }
    }

    @Test(expected = HTTPException.class)
    public void updateResultBadStatus() {
        final String resultId = "12345";
        final String baseUrl = "http://localhost/slick";
        final String mediaType = MediaType.APPLICATION_JSON;
        final String path = SlickSimpleClientImpl.UPDATE_RESULT_PATH(resultId) + "?status=NOT_TESTED";
        final Response responseOne = Response.status(301).type(mediaType).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilderOne;

            targetBuilderOne.get();
            result = responseOne;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try{
            simpleClient.updateStatus("12345", SlickResultStatus.NOT_TESTED);
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 301);
            throw e;
        }
    }

    @Test
    public void updateResult() {
        final String resultId = "12345";
        final String baseUrl = "http://localhost/slick";
        final String mediaType = MediaType.APPLICATION_JSON;
        final String path = SlickSimpleClientImpl.UPDATE_RESULT_PATH(resultId) + "?status=NO_RESULT";
        final Response responseOne = Response.status(200).type(mediaType).build();

        new Expectations() {{
            restClient.target(baseUrl);
            result = targetOne;

            targetOne.path(path);
            result = targetTwo;

            targetTwo.request(mediaType);
            result = targetBuilderOne;

            targetBuilderOne.get();
            result = responseOne;
        }};

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        simpleClient.updateStatus("12345", SlickResultStatus.NO_RESULT);
    }

}
