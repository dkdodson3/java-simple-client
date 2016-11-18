package com.slickqa.client.rules;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Keith on 11/15/16.
 */
public class SlickSuiteRuleTestTwo {
    @Rule
    public SlickResultRule result = new SlickResultRule();

    @SlickMetaData(
            title = "SlickResultRule3",
            component = "bar3",
            feature = "bob3",
            automationKey = "blah3",
            automationId = "burp3",
            automationTool = "bike3",
            steps = {
                    @Step(step = "13", expectation = "23"),
                    @Step(step = "33", expectation = "43")
            }
    )
    @Test
    public void verifyRuleTest3() {
        Assert.assertTrue(true);
    }



    @SlickMetaData(
            title = "SlickResultRule4",
            component = "bar4",
            feature = "bob4",
            automationKey = "blah4",
            automationId = "burp4",
            automationTool = "bike4",
            steps = {
                    @Step(step = "14", expectation = "24"),
                    @Step(step = "34", expectation = "44")
            }
    )
    @Test
    public void verifyRuleTest4() {
        Assert.assertTrue(false);
    }


}
