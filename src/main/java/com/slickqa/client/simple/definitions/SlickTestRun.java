package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.slickqa.client.simple.utils.JsonUtil;
import lombok.NonNull;

import javax.ws.rs.client.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickTestRun {
    public final static String MESSAGE_PROJECT_NAME_OR_ID_EMPTY = "Project Name and Id cannot both be empty";

    private final Long started;
    private final Long finished;
    private final SlickIdentity project;
    private final SlickIdentity release;
    private final SlickIdentity build;
    private final SlickIdentity testPlan;
    private final SlickIdentity testRun;
    private final List<SlickResult> results;

    public SlickTestRun(Long started, Long finished, SlickIdentity project, SlickIdentity release, SlickIdentity build, SlickIdentity testPlan, SlickIdentity testRun, List<SlickResult> results) {
        if (Strings.isNullOrEmpty(project.getId()) && Strings.isNullOrEmpty(project.getName())) {
            throw new IllegalArgumentException(MESSAGE_PROJECT_NAME_OR_ID_EMPTY);
        }

        this.started = started;
        this.finished = finished;
        this.project = project;
        this.release = release;
        this.build = build;
        this.testPlan = testPlan;
        this.testRun = testRun;
        this.results = results;
    }

    public Long getStarted() {
        return started;
    }

    public Long getFinished() {
        return finished;
    }

    public SlickIdentity getProject() {
        return project;
    }

    public SlickIdentity getRelease() {
        return release;
    }

    public SlickIdentity getBuild() {
        return build;
    }

    public SlickIdentity getTestPlan() {
        return testPlan;
    }

    public SlickIdentity getTestRun() {
        return testRun;
    }

    public List<SlickResult> getResults() {
        return results;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        ArrayNode resultsNode = mapper.createArrayNode();
        for (SlickResult result : this.getResults()) {
            resultsNode.add(result.toObjectNode());
        }

        objectNode.put("started", this.getStarted());
        objectNode.put("finished", this.getFinished());
        objectNode.put("project", this.getProject().toObjectNode());
        objectNode.put("release", this.getRelease().toObjectNode());
        objectNode.put("build", this.getBuild().toObjectNode());
        objectNode.put("testPlan", this.getTestPlan().toObjectNode());
        objectNode.put("testRun", this.getTestRun().toObjectNode());
        objectNode.put("results", resultsNode);

        return objectNode;
    }

    public static SlickTestRun fromJsonNode(JsonNode node) {
        ArrayList<SlickResult> results = new ArrayList<>();
        for (JsonNode result : node.get("results")) {
            results.add(SlickResult.fromJsonNode(result));
        }

        return new SlickTestRun(
                node.get("started").longValue(),
                node.get("finished").longValue(),
                SlickIdentity.fromJsonNode(node.get("project")),
                SlickIdentity.fromJsonNode(node.get("release")),
                SlickIdentity.fromJsonNode(node.get("build")),
                SlickIdentity.fromJsonNode(node.get("testPlan")),
                SlickIdentity.fromJsonNode(node.get("testRun")),
                results
        );
    }


    public static SlickTestRunBuilder builder() {
        return new SlickTestRunBuilder();
    }

}