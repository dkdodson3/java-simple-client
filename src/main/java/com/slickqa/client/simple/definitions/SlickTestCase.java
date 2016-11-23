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
    private String component;
    private String feature;
    private String automationId;
    private String automationKey;
    private String automationTool;
    private String name;
    private List<SlickStep> steps;

    public final static String MESSAGE_EMPTY_AUTOMATION = "Must have either automationId or automationKey";

    public SlickTestCase(@NonNull String name,
                         String component,
                         String feature,
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

    public void setComponent(String component) {
        this.component = component;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setAutomationId(String automationId) {
        this.automationId = automationId;
    }

    public void setAutomationKey(String automationKey) {
        this.automationKey = automationKey;
    }

    public void setAutomationTool(String automationTool) {
        this.automationTool = automationTool;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(List<SlickStep> steps) {
        this.steps = steps;
    }

    public String getComponent() {
        return component;
    }

    public String getFeature() {
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
        objectNode.put("component", this.getComponent());
        objectNode.put("feature", this.getFeature());
        objectNode.put("automationId", this.getAutomationId());
        objectNode.put("automationKey", this.getAutomationKey());
        objectNode.put("automationTool", this.getAutomationTool());
        objectNode.put("steps", stepsNode);

        return objectNode;
    }

    public static SlickTestCase fromJsonNode(JsonNode node){
        ArrayList<SlickStep> steps = new ArrayList<>();
        for (JsonNode step : node.get("steps")) {
            steps.add(SlickStep.fromJsonNode(step));
        }

        return new SlickTestCase(
                node.get("name").textValue(),
                node.get("component").textValue(),
                node.get("feature").textValue(),
                node.get("automationId").textValue(),
                node.get("automationKey").textValue(),
                node.get("automationTool").textValue(),
                steps
        );
    }

}
