package com.slickqa.client.simple.definitions;

public class SlickResultBuilder {
    private String id;
    private SlickTestCase testCase;
    private String reason;
    private Long started;
    private Long finished;
    private SlickResultStatus status;

    public SlickResultBuilder addId(String id) {
        this.id = id;
        return this;
    }

    public SlickResultBuilder addTestCase(SlickTestCase testCase) {
        this.testCase = testCase;
        return this;
    }

    public SlickResultBuilder addReason(String reason) {
        this.reason = reason;
        return this;
    }

    public SlickResultBuilder addStarted(Long started) {
        this.started = started;
        return this;
    }

    public SlickResultBuilder addFinished(Long finished) {
        this.finished = finished;
        return this;
    }

    public SlickResultBuilder addStatus(SlickResultStatus status) {
        this.status = status;
        return this;
    }

    public SlickResult build() {
        return new SlickResult(id, testCase, reason, started, finished, status);
    }
}