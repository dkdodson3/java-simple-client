package com.slickqa.client.annotations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Keith on 11/14/16.
 */
public class AnnotationTests {
    @Rule
    public TestName testName = new TestName();

    @SlickMetaData(
            title = "SlickResultRule",
            component = "bar",
            feature = "bob",
            automationKey = "blah",
            automationId = "burp",
            automationTool = "bike",
            steps = {
                    @Step(step = "1", expectation = "2"),
                    @Step(step = "3", expectation = "4")
            }
    )
    @Test
    public void verifyRuleTests() throws NoSuchMethodException {
        Method currentMethod = getClass().getMethod(testName.getMethodName());
        SlickMetaData metaData = currentMethod.getAnnotation(SlickMetaData.class);
        assertEquals(metaData.title(), "SlickResultRule");
        assertEquals(metaData.component(), "bar");
        assertEquals(metaData.feature(), "bob");
        assertEquals(metaData.automationKey(), "blah");
        assertEquals(metaData.automationId(), "burp");
        assertEquals(metaData.automationTool(), "bike");
        for (Step step : metaData.steps()) {
            assertNotNull(step.step());
        }
    }


}
