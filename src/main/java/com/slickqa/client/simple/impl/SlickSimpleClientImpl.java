package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleClientImpl implements SlickSimpleClient {
    public final static String MEDIA_TYPE = MediaType.APPLICATION_JSON;

    // Simple Slick Paths
    public static String CREATE_TEST_RUN_PATH = "/api/simple/create_test_run";
    public static String UPDATE_RESULT_PATH(String resultId) { return "/api/simple/result/" + resultId; }
    public static String LOG_PATH(String resultId) { return UPDATE_RESULT_PATH(resultId) + "/logs"; }
    public static String CREATE_FILE_PATH(String resultId) { return UPDATE_RESULT_PATH(resultId) + "/create_file"; }
    public static String UPLOAD_FILE_PATH(String resultId, String fileId) { return UPDATE_RESULT_PATH(resultId) + "/" + fileId; }

    private final String baseUrl;
    private final Client restClient;

    public SlickSimpleClientImpl(String baseUrl, Client restClient) {
        this.baseUrl = baseUrl;
        this.restClient = restClient;
    }

    private WebTarget getTarget() {
        return restClient.target(baseUrl);
    }

    private void checkStatus(Response response) {
        if ( response == null || response.getStatus() < 200 || response.getStatus() >= 300 ) {
            throw new HTTPException(response.getStatus());
        }
    }

    @Override
    public SlickTestRun addTestRun(SlickTestRun testRun) throws HTTPException{
        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(testRun, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(CREATE_TEST_RUN_PATH);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        checkStatus(response);

        // Change the response entity back to a TestRun and return it
        Entity entity = (Entity) response.getEntity();
        return (SlickTestRun) entity.getEntity();
    }

    @Override
    public void updateStatus(String resultId, SlickResultStatus status) throws HTTPException {
        // Getting the Simple Slick Path
        String path = UPDATE_RESULT_PATH(resultId) + "?status=" + status.toString();

        // Performing a HttpGet to the resultID with the status
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.get();

        checkStatus(response);
    }

    @Override
    public void addLog(SlickLog slickLog) throws HTTPException, IllegalArgumentException {
        // Getting the Simple Slick Path
        String path = LOG_PATH(slickLog.getResultId());

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(slickLog, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        checkStatus(response);
    }

    @Override
    public void addFile(SlickFile slickFile) {
        // Verify file is valid
        // Get FileType
        // Call CreateFile on Slick
        // Break the file into chunks
        // Upload the chunks to Slick
    }
}
