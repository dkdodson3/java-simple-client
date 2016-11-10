package com.slickqa.client.simple.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import static com.slickqa.client.simple.utils.JsonUtil.mapper;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleClientImpl implements SlickSimpleClient {
    private final String MEDIA_TYPE = MediaType.APPLICATION_JSON;
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

        if (response == null) {
            throw new NullPointerException("No Response...");
        }

        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            throw new HTTPException(response.getStatus());
        }
    }

    private String getEntityData(Response response) throws IOException {
        // Change the response entity back to a TestRun and return it
        String responseValue;
        try {
            responseValue = ((Entity) response.getEntity()).getEntity().toString();
        } catch (ClassCastException e) {
            throw new IOException("Could not convert response to entity: "
                    + response.getEntity().getClass().getName() + " - "
                    + response.getEntity().toString());
        }

        return responseValue;
    }

    @Override
    public SlickTestRun addTestRun(SlickTestRun testRun) throws HTTPException, IOException {
        String path = "/api/simple/testruns";

        Entity entityData = Entity.entity(testRun.toObjectNode(), MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        checkStatus(response);
        JsonNode jsonNode = mapper.readTree(getEntityData(response));

        return SlickTestRun.fromJsonNode(jsonNode);
    }

    @Override
    public ArrayList<SlickResult> addResults(String testRunId, ArrayList<SlickResult> results) throws IOException {
        String path = "/api/simple/" + testRunId + "/results";
        ArrayList<SlickResult> createList = new ArrayList<>();
        ArrayList<SlickResult> updateList = new ArrayList<>();

        for (SlickResult result: results) {
            if (result.getId() == null) {
                createList.add(result);
            } else {
                updateList.add(result);
            }
        }

        ArrayList<SlickResult> returnedResultList = new ArrayList<>();
        if (createList.size() > 0) {
            returnedResultList.addAll(createResults(path, createList));
        }

        if (updateList.size() > 0) {
            returnedResultList.addAll(updateResults(path, updateList));
        }

        return returnedResultList;
    }

    private ArrayList<SlickResult> createResults(String path, ArrayList<SlickResult> results) throws HTTPException, IOException {

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickResult result : results) {
            mainArray.add(result.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(mainArray, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        checkStatus(response);
        JsonNode jsonNode = mapper.readTree(getEntityData(response));

        ArrayList<SlickResult> returnResults = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            returnResults.add(SlickResult.fromJsonNode(node));
        }

        return returnResults;
    }

    private ArrayList<SlickResult> updateResults(String path, ArrayList<SlickResult> results) throws HTTPException, IOException {
        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickResult result : results) {
            mainArray.add(result.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(mainArray, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);

        Response response = request.put(entityData);

        checkStatus(response);
        JsonNode jsonNode = mapper.readTree(getEntityData(response));

        ArrayList<SlickResult> returnResults = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            returnResults.add(SlickResult.fromJsonNode(node));
        }

        return returnResults;
    }

    @Override
    public void addLogs(String testRunId, String resultId, ArrayList<SlickLog> slickLogs) throws HTTPException {
        // Getting the Simple Slick Path
        String path = "/api/simple/" + testRunId + "/results/" + resultId + "/log";

        ArrayNode mainArray = mapper.createArrayNode();
        for (SlickLog log : slickLogs) {
            mainArray.add(log.toObjectNode());
        }

        // Prepping the data and posting it to Simple Slick
        Entity entityData = Entity.entity(mainArray, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        checkStatus(response);
    }

    @Override
    public ArrayList<SlickFile> addFiles(String testRunId, String resultId, ArrayList<SlickFile> slickFiles) throws IOException {
        String path = "/api/simple/" + testRunId + "/results/" + resultId + "/files";

        ArrayList<SlickFile> returnedSlickFiles = new ArrayList<>();
        for (SlickFile slickFile: slickFiles) {
            SlickFile tempSlickFile = slickFile;
            if (slickFile.getIdentity().getId() == null) {
                tempSlickFile = createFile(path, slickFile);
            }

            uploadFile(path, tempSlickFile);

            returnedSlickFiles.add(tempSlickFile);
        }

        return returnedSlickFiles;
    }

    private SlickFile createFile(String path, SlickFile slickFile) throws IOException {
        if (slickFile.getMimeType() == null) {
            URL url = new URL("file://" +  slickFile.getFilePath());
            slickFile.setMimeType(url.openConnection().getContentType());
        }

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("filename", slickFile.getIdentity().getName());
        objectNode.put("mimetype", slickFile.getMimeType());

        Entity entityData = Entity.entity(objectNode, MEDIA_TYPE);
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);
        Response response = request.post(entityData);

        JsonNode jsonNode = mapper.readTree(getEntityData(response));
        slickFile.setChunkSize(jsonNode.get("chunksize").intValue());
        slickFile.getIdentity().setId(jsonNode.get("id").textValue());

        return slickFile;
    }

    private void uploadFile(String path, SlickFile slickFile) throws IOException {
        path = path + "/" + slickFile.getIdentity().getId();
        WebTarget currentTarget = this.getTarget().path(path);
        Invocation.Builder request = currentTarget.request(MEDIA_TYPE);

        File file = new File(slickFile.getFilePath().toString());
        FileInputStream stream = new FileInputStream(file);

        byte[] buffer = new byte[slickFile.getChunkSize()];
        int last_read;
        do {
            last_read = stream.read(buffer, 0, slickFile.getChunkSize());
            Entity entityData = Entity.entity(buffer, slickFile.getMimeType());

            Response response = request.post(entityData);
            checkStatus(response);

        } while (last_read == slickFile.getChunkSize());
    }

}
