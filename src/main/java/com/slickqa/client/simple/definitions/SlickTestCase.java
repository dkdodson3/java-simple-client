package com.slickqa.client.simple.definitions;

import com.google.common.base.Strings;
import lombok.NonNull;

import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickTestCase {
    private final String component;
    private final String feature;
    private final String automationId;
    private final String automationKey;
    private final String automationTool;
    private final String testTitle;
    private final List<String> steps;
    private final List<String> expectations;

    public final static String MESSAGE_EMPTY_AUTOMATION = "Must have either automationId or automationKey";

    public SlickTestCase(String component,
                         String feature,
                         String automationId,
                         String automationKey,
                         @NonNull String automationTool,
                         @NonNull String testTitle,
                         List<String> steps,
                         List<String> expectations) {

        if (Strings.isNullOrEmpty(automationId) && Strings.isNullOrEmpty(automationKey)) {
            throw new IllegalArgumentException(MESSAGE_EMPTY_AUTOMATION);
        }

        this.component = component;
        this.feature = feature;
        this.automationId = automationId;
        this.automationKey = automationKey;
        this.automationTool = automationTool;
        this.testTitle = testTitle;
        this.steps = steps;
        this.expectations = expectations;
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

    public static SlickTestCaseBuilder builder() {
        return new SlickTestCaseBuilder();
    }

}
