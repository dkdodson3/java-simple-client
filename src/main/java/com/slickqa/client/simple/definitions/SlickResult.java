package com.slickqa.client.simple.definitions;

import lombok.NonNull;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickResult {
    private final SlickIdentity identity;
    private final SlickTestCase testCase;
    private SlickResultStatus status;

    public SlickResult(@NonNull SlickIdentity identity, @NonNull SlickTestCase testCase, SlickResultStatus status) {
        this.identity = identity;
        this.testCase = testCase;

        if (status == null) {
            status = SlickResultStatus.NO_RESULT;
        }
        this.status = status;
    }

    public SlickIdentity getIdentity() {
        return this.identity;
    }

    public SlickTestCase getTestCase() {
        return this.testCase;
    }

    public SlickResultStatus getStatus() {
        return this.status;
    }

    public static SlickResultBuilder builder() {
        return new SlickResultBuilder();
    }

}
