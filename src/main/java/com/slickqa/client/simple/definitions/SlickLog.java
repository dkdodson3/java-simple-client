package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.utils.JsonUtil;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickLog {
    private final String exceptionClassName;
    private final String level;
    private final String exceptionMessage;
    private final Long entryTime;
    private final String loggerName;
    private final String exceptionStackTrace;
    private final String message;

    public SlickLog(String exceptionClassName, String level, String exceptionMessage, Long entryTime, String loggerName, String exceptionStackTrace, String message) {
        this.exceptionClassName = exceptionClassName;
        this.level = level;
        this.exceptionMessage = exceptionMessage;
        this.entryTime = entryTime;
        this.loggerName = loggerName;
        this.exceptionStackTrace = exceptionStackTrace;
        this.message = message;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public String getLevel() {
        return level;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Long getEntryTime() {
        return entryTime;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public String getMessage() {
        return message;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("exceptionClassName", this.getExceptionClassName());
        objectNode.put("level", this.getLevel());
        objectNode.put("exceptionMessage", this.getExceptionMessage());
        objectNode.put("entryTime", this.getEntryTime());
        objectNode.put("loggerName", this.getLoggerName());
        objectNode.put("exceptionStackTrace", this.getExceptionStackTrace());
        objectNode.put("message", this.getMessage());
        return objectNode;
    }

    public static SlickLog fromJsonNode(JsonNode node) {
        return new SlickLog(node.get("exceptionClassName").textValue(),
                node.get("level").textValue(),
                node.get("exceptionMessage").textValue(),
                node.get("entryTime").asLong(),
                node.get("loggerName").textValue(),
                node.get("exceptionStackTrace").textValue(),
                node.get("message").textValue());
    }

    public static SlickLogBuilder builder() {
        return new SlickLogBuilder();
    }

}
