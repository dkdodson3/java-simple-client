package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.*;
import com.slickqa.client.simple.utils.JsonUtil;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickTestCase {
    private final SlickIdentity component;
    private final SlickIdentity feature;
    private final String automationId;
    private final String automationKey;
    private final String automationTool;
    private final String name;
    private final List<SlickStep> steps;

    public final static String MESSAGE_EMPTY_AUTOMATION = "Must have either automationId or automationKey";

    public SlickTestCase(@NonNull String name,
                         SlickIdentity component,
                         SlickIdentity feature,
                         String automationId,
                         String automationKey,
                         @NonNull String automationTool,
                         List<SlickStep> steps) {

        if (Strings.isNullOrEmpty(automationId) && Strings.isNullOrEmpty(automationKey)) {
            throw new IllegalArgumentException(MESSAGE_EMPTY_AUTOMATION);
        }

        this.name = name;
        this.component = component;
        this.feature = feature;
        this.automationId = automationId;
        this.automationKey = automationKey;
        this.automationTool = automationTool;
        this.steps = steps;
    }

    public SlickIdentity getComponent() {
        return component;
    }

    public SlickIdentity getFeature() {
        return feature;
    }

    public String getAutomationId() {
        return automationId;
    }

    public String getAutomationKey() {
        return automationKey;
    }

    public String getAutomationTool() {
        return automationTool;
    }

    public String getName() {
        return name;
    }

    public List<SlickStep> getSteps() {
        return steps;
    }

    public static SlickTestCaseBuilder builder() {
        return new SlickTestCaseBuilder();
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        ArrayNode stepsNode = mapper.createArrayNode();
        for (SlickStep step : this.getSteps()) {
            stepsNode.add(step.toObjectNode());
        }

        objectNode.put("name", this.getName());
        objectNode.put("component", this.getComponent().toObjectNode());
        objectNode.put("feature", this.getFeature().toObjectNode());
        objectNode.put("automationId", this.getAutomationId());
        objectNode.put("automationKey", this.getAutomationKey());
        objectNode.put("automationTool", this.getAutomationTool());
        objectNode.put("steps", stepsNode);

        return objectNode;
    }

    public static SlickTestCase fromJsonNode(JsonNode node){
        SlickIdentity component = SlickIdentity.fromJsonNode(node.get("component"));
        SlickIdentity feature = SlickIdentity.fromJsonNode(node.get("feature"));

        ArrayList<SlickStep> steps = new ArrayList<>();
        for (JsonNode step : node.get("steps")) {
            steps.add(SlickStep.fromJsonNode(step));
        }

        return new SlickTestCase(
                node.get("name").textValue(),
                component,
                feature,
                node.get("automationId").textValue(),
                node.get("automationKey").textValue(),
                node.get("automationTool").textValue(),
                steps
        );
    }

}
