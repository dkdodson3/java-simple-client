package com.slickqa.client.simple.definitions;

import lombok.NonNull;

import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickTestCase {
    private final String testCaseId;
    private final String component;
    private final String feature;
    private final String automationId;
    private final String automationKey;
    private final String automationTool;
    private final String testTitle;
    private final List<String> steps;
    private final List<String> expectations;

    public SlickTestCase(String testCaseId,
                         String component,
                         String feature,
                         String automationId,
                         String automationKey,
                         String automationTool,
                         @NonNull String testTitle,
                         List<String> steps,
                         List<String> expectations) {
        this.testCaseId = testCaseId;
        this.component = component;
        this.feature = feature;
        this.automationId = automationId;
        this.automationKey = automationKey;
        this.automationTool = automationTool;
        this.testTitle = testTitle;
        this.steps = steps;
        this.expectations = expectations;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public String getComponent() {
        return component;
    }

    public String getFeature() {
        return feature;
    }

    public String getAutomationId() {
        return automationId;
    }

    public String getAutomationKey() {
        return automationKey;
    }

    public String getAutomationTool() {
        return automationTool;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public List<String> getSteps() {
        return steps;
    }

    public List<String> getExpectations() {
        return expectations;
    }

    public static TestCaseBuilder builder() {
        return new TestCaseBuilder();
    }

}
