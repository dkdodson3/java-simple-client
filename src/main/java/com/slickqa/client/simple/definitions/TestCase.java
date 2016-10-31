package com.slickqa.client.simple.definitions;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */
@Builder
@Getter
public class TestCase {
    private String testCaseId;
    private String component;
    private String feature;
    private String automationId;
    private String automationKey;
    private String automationTool;
    private String testTitle;
    @Singular private List<String> steps;
    @Singular private List<String> expectations;
}
