package com.slickqa.client.simple.definitions;

public class ResultBuilder {
    private String resultId;
    private String status;
    private TestCase testCase;

    public ResultBuilder addResultId(String resultId) {
        this.resultId = resultId;
        return this;
    }

    public ResultBuilder addStatus(String status) {
        this.status = status;
        return this;
    }

    public ResultBuilder addTestCase(TestCase testCase) {
        this.testCase = testCase;
        return this;
    }

    public Result build() {
        return new Result(resultId, status, testCase);
    }
}