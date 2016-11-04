package com.slickqa.client.simple.definitions;

import lombok.NonNull;

public class SlickResultBuilder {
    private SlickIdentity identity;
    private SlickTestCase testCase;
    private SlickResultStatus status;

    public SlickResultBuilder addIdentity(SlickIdentity identity) {
        this.identity = identity;
        return this;
    }

    public SlickResultBuilder addTestCase(SlickTestCase testCase) {
        this.testCase = testCase;
        return this;
    }

    public SlickResultBuilder addStatus(SlickResultStatus status) {
        this.status = status;
        return this;
    }

    public SlickResult build() {
        return new SlickResult(identity, testCase, status);
    }
}