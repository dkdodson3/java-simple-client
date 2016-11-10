package com.slickqa.client.simple.definitions;

public class SlickLogBuilder {
    private String exceptionClassName;
    private String level;
    private String exceptionMessage;
    private Long entryTime;
    private String loggerName;
    private String exceptionStackTrace;
    private String message;

    public SlickLogBuilder addExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
        return this;
    }

    public SlickLogBuilder addLevel(String level) {
        this.level = level;
        return this;
    }

    public SlickLogBuilder addExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public SlickLogBuilder addEntryTime(Long entryTime) {
        this.entryTime = entryTime;
        return this;
    }

    public SlickLogBuilder addLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public SlickLogBuilder addExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
        return this;
    }

    public SlickLogBuilder addMessage(String message) {
        this.message = message;
        return this;
    }

    public SlickLog build() {
        return new SlickLog(exceptionClassName, level, exceptionMessage, entryTime, loggerName, exceptionStackTrace, message);
    }
}