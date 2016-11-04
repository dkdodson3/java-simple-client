package com.slickqa.client.simple.definitions;

import com.google.common.base.Strings;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SlickTestCaseBuilder {
    private String component;
    private String feature;
    private String automationId;
    private String automationKey;
    private String automationTool;
    private String testTitle;
    private List<String> steps;
    private List<String> expectations;

    public SlickTestCaseBuilder addComponent(String component) {
        this.component = component;
        return this;
    }

    public SlickTestCaseBuilder addFeature(String feature) {
        this.feature = feature;
        return this;
    }

    public SlickTestCaseBuilder addAutomationId(String automationId) {
        this.automationId = automationId;
        return this;
    }

    public SlickTestCaseBuilder addAutomationKey(String automationKey) {
        this.automationKey = automationKey;
        return this;
    }

    public SlickTestCaseBuilder addAutomationTool(String automationTool) {
        this.automationTool = automationTool;
        return this;
    }

    public SlickTestCaseBuilder addTestTitle(String testTitle) {
        this.testTitle = testTitle;
        return this;
    }

    public SlickTestCaseBuilder addStep(String step) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.add(step);
        return this;
    }

    public SlickTestCaseBuilder addSteps(List<String> steps) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.addAll(steps);
        return this;
    }

    public SlickTestCaseBuilder addExpectation(String expectation) {
        if (this.expectations == null) {
            this.expectations = new ArrayList<>();
        }

        this.expectations.add(expectation);
        return this;
    }

    public SlickTestCaseBuilder addExpectations(List<String> expectations) {
        if (this.expectations == null) {
            this.expectations = new ArrayList<>();
        }

        this.expectations.addAll(expectations);
        return this;
    }

    public SlickTestCase build() {
        if (Strings.isNullOrEmpty(automationId) && Strings.isNullOrEmpty(automationKey)) {
            throw new IllegalArgumentException(SlickTestCase.MESSAGE_EMPTY_AUTOMATION);
        }

        return new SlickTestCase(component, feature, automationId, automationKey, automationTool, testTitle, steps, expectations);
    }
}