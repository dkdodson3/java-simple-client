package com.slickqa.client.simple.definitions;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Keith on 10/25/16.
 */
@Builder
@Getter
public class Result {
    private String resultId;
    private String status;
    private TestCase testCase;
}
