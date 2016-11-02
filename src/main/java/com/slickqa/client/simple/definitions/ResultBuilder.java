package com.slickqa.client.simple.definitions;

public class ResultBuilder {
    private String resultId;
    private String status;
    private SlickTestCase testCase;

    public ResultBuilder addResultId(String resultId) {
        this.resultId = resultId;
        return this;
    }

    public ResultBuilder addStatus(String status) {
        this.status = status;
        return this;
    }

    public ResultBuilder addTestCase(SlickTestCase testCase) {
        this.testCase = testCase;
        return this;
    }

    public SlickResult build() {
        return new SlickResult(resultId, status, testCase);
    }
}