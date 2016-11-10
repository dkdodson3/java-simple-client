package com.slickqa.client.simple.definitions;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class SlickTestCaseBuilder {
    private SlickIdentity component;
    private SlickIdentity feature;
    private String automationId;
    private String automationKey;
    private String automationTool;
    private String name;
    private List<SlickStep> steps;

    public SlickTestCaseBuilder addComponent(SlickIdentity component) {
        this.component = component;
        return this;
    }

    public SlickTestCaseBuilder addFeature(SlickIdentity feature) {
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

    public SlickTestCaseBuilder addName(String name) {
        this.name = name;
        return this;
    }

    public SlickTestCaseBuilder addStep(SlickStep step) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.add(step);
        return this;
    }

    public SlickTestCaseBuilder addSteps(List<SlickStep> steps) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.steps.addAll(steps);
        return this;
    }

    public SlickTestCase build() {
        if (Strings.isNullOrEmpty(automationId) && Strings.isNullOrEmpty(automationKey)) {
            throw new IllegalArgumentException(SlickTestCase.MESSAGE_EMPTY_AUTOMATION);
        }

        return new SlickTestCase(name, component, feature, automationId, automationKey, automationTool, steps);
    }
}