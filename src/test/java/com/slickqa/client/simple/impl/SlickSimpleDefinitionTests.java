package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleTestUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.definitions.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Keith on 11/9/16.
 */
@RunWith(JMockit.class)
public class SlickSimpleDefinitionTests {

    @Test
    public void testCaseRoundTripSerializationTest() {
        SlickTestCase testCase1 = SlickSimpleTestUtils.getTestCase("Blah", null);
        ObjectNode jsonNodes = testCase1.toObjectNode();
        SlickTestCase testCase2 = SlickTestCase.fromJsonNode(jsonNodes);

        SlickSimpleTestUtils.validateTestCase(testCase1, testCase2, true);
    }

    @Test
    public void resultRoundTripSerializationTest() {
        SlickResult result1 = SlickSimpleTestUtils.getResult("Blah", "1234");
        ObjectNode jsonNodes = result1.toObjectNode();
        SlickResult result2 = SlickResult.fromJsonNode(jsonNodes);

        SlickSimpleTestUtils.validateResult(result1, result2, true);
    }

    @Test
    public void testRunRoundTripSerializationTest() {
        SlickTestRun testRun1 = SlickSimpleTestUtils.getTestRun("Blah", "1234");
        ObjectNode jsonNodes = testRun1.toObjectNode();
        SlickTestRun testRun2 = SlickTestRun.fromJsonNode(jsonNodes);

        SlickSimpleTestUtils.validateTestRun(testRun1, testRun2, true);
    }

    @Test
    public void testSlickLog() {
        SlickLog log1 = SlickSimpleTestUtils.getSlickLog("blah");
        ObjectNode jsonNodes = log1.toObjectNode();
        SlickLog log2 = SlickLog.fromJsonNode(jsonNodes);

        SlickSimpleTestUtils.validateLog(log1, log2);
    }



}
