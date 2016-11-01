package com.slickqa.client.simple.definitions;

import java.util.ArrayList;
import java.util.List;

public class TestCaseBuilder {
    private String testCaseId;
    private String component;
    private String feature;
    private String automationId;
    private String automationKey;
    private String automationTool;
    private String testTitle;
    private List<String> steps;
    private List<String> expectations;

    public TestCaseBuilder addTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
        return this;
    }

    public TestCaseBuilder addComponent(String component) {
        this.component = component;
        return this;
    }

    public TestCaseBuilder addFeature(String feature) {
        this.feature = feature;
        return this;
    }

    public TestCaseBuilder addAutomationId(String automationId) {
        this.automationId = automationId;
        return this;
    }

    public TestCaseBuilder addAutomationKey(String automationKey) {
        this.automationKey = automationKey;
        return this;
    }

    public TestCaseBuilder addAutomationTool(String automationTool) {
        this.automationTool = automationTool;
        return this;
    }

    public TestCaseBuilder addTestTitle(String testTitle) {
        this.testTitle = testTitle;
        return this;
    }

    public TestCaseBuilder addStep(String step) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.add(step);
        return this;
    }

    public TestCaseBuilder addSteps(List<String> steps) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.addAll(steps);
        return this;
    }

    public TestCaseBuilder addExpectation(String expectation) {
        if (this.expectations == null) {
            this.expectations = new ArrayList<>();
        }

        this.expectations.add(expectation);
        return this;
    }

    public TestCaseBuilder addExpectations(List<String> expectations) {
        if (this.expectations == null) {
            this.expectations = new ArrayList<>();
        }

        this.expectations.addAll(expectations);
        return this;
    }

    public TestCase build() {
        return new TestCase(testCaseId, component, feature, automationId, automationKey, automationTool, testTitle, steps, expectations);
    }
}