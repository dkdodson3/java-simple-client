package com.slickqa.client.simple;

import com.slickqa.client.simple.definitions.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Keith on 11/9/16.
 */
public class SlickSimpleTestUtils {

    public static SlickTestCase getTestCase(String text, String id) {
        SlickIdentity component = new SlickIdentity("component" + text, id);
        SlickIdentity feature = new SlickIdentity("feature" + text, id);

        SlickStep step1 = new SlickStep("First Step: " + text, "Show me the money: " + text);
        SlickStep step2 = new SlickStep("Second Step: " + text, "Show me the gold: " + text);

        return  SlickTestCase.builder()
                .addName(text)
                .addAutomationKey(text)
                .addStep(step1)
                .addStep(step2)
                .addComponent(component)
                .addFeature(feature)
                .addAutomationId(text)
                .addAutomationTool(text)
                .build();
    }

    public static SlickResult getResult(String text, String id) {
        SlickTestCase testCase = getTestCase(text, "testcase" + id);
        return SlickResult.builder()
                .addStatus(SlickResultStatus.BROKEN_TEST)
                .addStarted(System.currentTimeMillis())
                .addFinished(System.currentTimeMillis() + 1)
                .addId(id)
                .addReason("reason: " + text)
                .addTestCase(testCase)
                .build();
    }

    public static SlickTestRun getTestRun(String text, String id) {
        return SlickTestRun.builder()
                .addStarted(System.currentTimeMillis())
                .addFinished(System.currentTimeMillis() + 1)
                .addProject(new SlickIdentity("project:" + text, "1" + id))
                .addRelease(new SlickIdentity("release:" + text, "2" + id))
                .addBuild(new SlickIdentity("build:" + text, "3" + id))
                .addTestPlan(new SlickIdentity("testPlan:" + text, "4" + id))
                .addTestRun(new SlickIdentity("testRun:" + text, "5" + id))
                .addResult(getResult("Blah1" + text, "1234" + id))
                .addResult(getResult("Blah2" + text, "1235" + id))
                .build();
    }

    public static SlickLog getSlickLog(String text) {
        return SlickLog.builder()
                .addEntryTime(System.currentTimeMillis())
                .addExceptionClassName("ClassException: " + text)
                .addExceptionMessage("ExceptionMessage: " + text)
                .addExceptionStackTrace("StackTrace: " + text)
                .addLevel("Level: " + text)
                .addLoggerName("LoggerName: " + text)
                .addMessage("Message: " + text)
                .build();
    }

    public static void validateSlickIdentity(SlickIdentity identity1, SlickIdentity identity2, Boolean id) {
        if (id) {
            assertEquals(identity1.getId(), identity2.getId());
        } else {
            assertNotNull(identity2.getId());
        }

        assertEquals(identity1.getName(), identity2.getName());
    }

    public static void validateStep(SlickStep step1, SlickStep step2) {
        assertEquals(step1.getName(), step2.getName());
        assertEquals(step1.getExpectedResults(), step2.getExpectedResults());
    }

    public static void validateLog(SlickLog log1, SlickLog log2) {
        assertEquals(log1.getMessage(), log2.getMessage());
        assertEquals(log1.getExceptionMessage(), log2.getExceptionMessage());
        assertEquals(log1.getExceptionClassName(), log2.getExceptionClassName());
        assertEquals(log1.getExceptionStackTrace(), log2.getExceptionStackTrace());
        assertEquals(log1.getEntryTime(), log2.getEntryTime());
        assertEquals(log1.getLevel(), log2.getLevel());
        assertEquals(log1.getLoggerName(), log2.getLoggerName());
    }

    public static void validateTestCase(SlickTestCase testCase1, SlickTestCase testCase2, Boolean id) {
        assertEquals(testCase1.getName(), testCase2.getName());
        assertEquals(testCase1.getAutomationId(), testCase2.getAutomationId());
        assertEquals(testCase1.getAutomationKey(), testCase2.getAutomationKey());
        assertEquals(testCase1.getAutomationTool(), testCase2.getAutomationTool());

        for (int i = 0; i < testCase1.getSteps().size(); i++) {
            validateStep(testCase1.getSteps().get(i), testCase2.getSteps().get(i));
        }

        validateSlickIdentity(testCase1.getComponent(), testCase2.getComponent(), id);
        validateSlickIdentity(testCase1.getFeature(), testCase2.getFeature(), id);
    }

    public static void validateResult(SlickResult result1, SlickResult result2, Boolean id) {
        assertEquals(result1.getId(), result2.getId());
        assertEquals(result1.getStarted(), result2.getStarted());
        assertEquals(result1.getFinished(), result2.getFinished());
        assertEquals(result1.getReason(), result2.getReason());
        assertEquals(result1.getStatus(), result2.getStatus());

        validateTestCase(result1.getTestCase(), result2.getTestCase(), id);
    }

    public static void validateTestRun(SlickTestRun testRun1, SlickTestRun testRun2, Boolean id) {
        assertEquals(testRun1.getStarted(), testRun2.getStarted());
        assertEquals(testRun1.getFinished(), testRun2.getFinished());

        validateSlickIdentity(testRun1.getProject(), testRun2.getProject(), id);
        validateSlickIdentity(testRun1.getRelease(), testRun2.getRelease(), id);
        validateSlickIdentity(testRun1.getBuild(), testRun2.getBuild(), id);
        validateSlickIdentity(testRun1.getTestPlan(), testRun2.getTestPlan(), id);
        validateSlickIdentity(testRun1.getTestRun(), testRun2.getTestRun(), id);

        for (int i = 0; i < testRun1.getResults().size(); i++) {
            validateResult(testRun1.getResults().get(i), testRun2.getResults().get(i), id);
        }

    }
}
