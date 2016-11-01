package com.slickqa.client.simple.definitions;

/**
 * Created by Keith on 10/25/16.
 */
public class Result {
    private final String resultId;
    private final String status;
    private final TestCase testCase;

    public Result(String resultId, String status, TestCase testCase) {
        this.resultId = resultId;
        this.status = status;
        this.testCase = testCase;
    }

    public String getResultId() {
        return this.resultId;
    }

    public String getStatus() {
        return this.status;
    }

    public TestCase getTestCase() {
        return this.testCase;
    }

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }
}
