package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.utils.JsonUtil;

/**
 * Created by Keith on 10/25/16.
 */
public class SlickIdentity {
    private String name;
    private String id;

    public SlickIdentity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", this.getName());
        objectNode.put("id", this.getId());
        return objectNode;
    }

    public static SlickIdentity fromJsonNode(JsonNode node) {
        return new SlickIdentity(
                node.get("name").textValue(),
                node.get("id").textValue());
    }
}
