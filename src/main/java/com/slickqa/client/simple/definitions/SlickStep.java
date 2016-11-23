package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.utils.JsonUtil;

/**
 * Created by Keith on 11/7/16.
 */
public class SlickStep {
    private String name;
    private String expectedResults;

    public SlickStep(String name, String expectedResults) {
        this.name = name;
        this.expectedResults = expectedResults;
    }

    public String getName() {
        return name;
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", this.getName());
        objectNode.put("expectedResults", this.getExpectedResults());
        return objectNode;
    }

    public static SlickStep fromJsonNode(JsonNode node) {
        return new SlickStep(
                node.get("name").textValue(),
                node.get("expectedResults").textValue());
    }
}
