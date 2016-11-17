package com.slickqa.client.Rules;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Keith on 11/15/16.
 */
public class SlickSuiteRuleTestOne {
    @ClassRule
    public static SlickSuiteRule slickSuite = new SlickSuiteRule();

    @BeforeClass
    public static void setup() {
        slickSuite.addResults();
    }

    @Rule
    public SlickResultRule result = new SlickResultRule();

    @SlickMetaData(
            title = "SlickResultRule1",
            component = "bar1",
            feature = "bob1",
            automationKey = "blah1",
            automationId = "burp1",
            automationTool = "bike1",
            steps = {
                    @Step(step = "11", expectation = "21"),
                    @Step(step = "31", expectation = "41")
            }
    )
    @Test public void verifyRuleTest1() throws NoSuchMethodException, NoSuchFieldException {
        SlickMetaData metaData = result.getMetaData();
    }



    @SlickMetaData(
            title = "SlickResultRule2",
            component = "bar2",
            feature = "bob2",
            automationKey = "blah2",
            automationId = "burp2",
            automationTool = "bike2",
            steps = {
                    @Step(step = "12", expectation = "22"),
                    @Step(step = "32", expectation = "42")
            }
    )
    @Test public void verifyRuleTest2() throws NoSuchMethodException, NoSuchFieldException {
        SlickMetaData metaData = result.getMetaData();
    }


}
