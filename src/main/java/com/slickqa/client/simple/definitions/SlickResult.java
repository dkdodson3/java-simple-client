package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.utils.JsonUtil;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickResult {
    private final String id;
    private final SlickTestCase testCase;
    private final String reason;
    private final Long started;
    private final Long finished;
    private SlickResultStatus status;

    public SlickResult(String id, SlickTestCase testCase, String reason, Long started, Long finished, SlickResultStatus status) {
        this.id = id;
        this.testCase = testCase;
        this.reason = reason;
        this.started = started;
        this.finished = finished;

        if (status == null) {
            this.status = SlickResultStatus.NO_RESULT;
        } else {
            this.status = status;
        }
    }

    public String getId() {
        return id;
    }

    public SlickTestCase getTestCase() {
        return testCase;
    }

    public String getReason() {
        return reason;
    }

    public Long getStarted() {
        return started;
    }

    public Long getFinished() {
        return finished;
    }

    public SlickResultStatus getStatus() {
        return status;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        objectNode.put("id", this.getId());
        objectNode.put("testCase", this.getTestCase().toObjectNode());
        objectNode.put("reason", this.getReason());
        objectNode.put("started", this.getStarted());
        objectNode.put("finished", this.getFinished());
        objectNode.put("status", this.getStatus().toString());

        return objectNode;
    }

    public static SlickResult fromJsonNode(JsonNode node) {
        SlickTestCase testCase = SlickTestCase.fromJsonNode(node.get("testCase"));
        SlickResultStatus status = SlickResultStatus.NOT_TESTED;

        if (node.get("status").textValue() != null) {
            status = SlickResultStatus.valueOf(node.get("status").textValue());
        }

        return new SlickResult(
                node.get("id").textValue(),
                testCase,
                node.get("reason").textValue(),
                node.get("started").longValue(),
                node.get("finished").longValue(),
                status
        );
    }

    public static SlickResultBuilder builder() {
        return new SlickResultBuilder();
    }

}
