package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClientFactory;
import com.slickqa.client.simple.definitions.*;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import mockit.internal.state.TestRun;
import org.junit.Assert;
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
    Invocation.Builder targetBuilder;

    @Test
    public void initializeClientWithoutClient() {
        final String baseUrl = "http://localhost/slick";

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl);
        assertNotNull(simpleClient);
        assertThat(simpleClient, instanceOf(SlickSimpleClientImpl.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTestRunNoProjectName() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = "/api/simple/create_test_run";

        SlickIdentity testProject = new SlickIdentity(null, null);
        SlickTestRun testRun = SlickTestRun.builder().addProject(testProject).build();
        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try {
            simpleClient.addTestRun(testRun);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Project Name and Id cannot both be empty");
            throw e;
        }

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
        SlickTestRun testRun = new TestRunBuilder().addProject(testProject).build();


        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addProject(testProjectRet).build();

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
        SlickTestRun returnValue = simpleClient.addTestRun(testRun);
        assertEquals(testRunRetValue, returnValue);
    }

    @Test(expected = HTTPException.class)
    public void getTestRunBadStatus() {
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = "/api/simple/create_test_run";

        String projectName = "foo";
        String projectId = "1234-BOB";

        String testRunName = "bar";
        String testRunId = "1234-FRANK";

        SlickIdentity testProject = new SlickIdentity(projectName, null);
        SlickTestRun testRun = SlickTestRun.builder().addProject(testProject).build();

        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addProject(testProjectRet).build();

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
        final String path = "/api/simple/create_test_run";

        String projectName = "foo";
        String projectId = "1234-BOB";

        String testRunName = "bar";
        String testRunId = "1234-FRANK";

        ArrayList<String> stepsOne = new ArrayList<>();
        ArrayList<String> expectationsOne = new ArrayList<>();
        stepsOne.add("1) test step");
        stepsOne.add("2) test step");
        expectationsOne.add("1) test expectation");
        expectationsOne.add("2) test expectation");

        SlickTestCase testCaseOne = SlickTestCase.builder()
                .addAutomationKey("foo")
                .addTestTitle("bar")
                .addSteps(stepsOne)
                .addExpectations(expectationsOne)
                .build();
        SlickResult resultOne = SlickResult.builder().addTestCase(testCaseOne).build();
        SlickIdentity testProject = new SlickIdentity(projectName, null);
        SlickTestRun testRun = new TestRunBuilder().addProject(testProject).build();


        SlickIdentity testProjectRet = new SlickIdentity(projectName, projectId);
        SlickIdentity testRunRet = new SlickIdentity(testRunName, testRunId);
        ArrayList<String> stepsTwo = new ArrayList<>();
        ArrayList<String> expectationsTwo = new ArrayList<>();
        stepsTwo.add("1) test step");
        stepsTwo.add("2) test step");
        expectationsTwo.add("1) test expectation");
        expectationsTwo.add("2) test expectation");

        SlickTestCase testCaseTwo = SlickTestCase.builder()
                .addAutomationKey("foo")
                .addTestTitle("bar")
                .addSteps(stepsTwo)
                .addExpectations(expectationsTwo)
                .build();
        SlickResult resultTwo = SlickResult.builder().addTestCase(testCaseTwo).build();
        SlickTestRun testRunRetValue = SlickTestRun.builder().addTestRun(testRunRet).addResult(resultTwo).addProject(testProjectRet).build();

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
        SlickTestRun returnValue = simpleClient.addTestRun(testRun);
        assertEquals(testRunRetValue, returnValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLogsEmptyResultId() {
        final String baseUrl = "http://localhost/slick";

        SlickLog logs = SlickLog.builder().addResultId("").addLog("foo").build();

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try {
            simpleClient.addLogs(logs);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "ResultId is null or empty");
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLogsEmptyLogs() {
        final String baseUrl = "http://localhost/slick";

        SlickLog logs = SlickLog.builder().addResultId("1234").build();

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try {
            simpleClient.addLogs(logs);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Logs are null or empty");
            throw e;
        }
    }

    @Test(expected = HTTPException.class)
    public void addLogsBadStatus() {
        String resultId = "1234";
        final String mediaType = MediaType.APPLICATION_JSON;
        final String baseUrl = "http://localhost/slick";
        final String path = "/api/simple/result/" + resultId;
        final Response response = Response.status(301).type(mediaType).build();

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

        SlickLog logs = SlickLog.builder().addResultId(resultId).addLog("foo").build();

        SlickSimpleClientImpl simpleClient = SlickSimpleClientFactory.getSlickSimpleClient(baseUrl, restClient);
        try{
            simpleClient.addLogs(logs);
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 301);
            throw e;
        }
    }

}
