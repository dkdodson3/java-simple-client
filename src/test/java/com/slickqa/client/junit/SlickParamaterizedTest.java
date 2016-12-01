package com.slickqa.client.junit;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Keith on 11/23/16.
 */
@RunWith(Parameterized.class)
public class SlickParamaterizedTest {
    @Rule
    public SlickResultRule result = new SlickResultRule();

    @Parameters(name = "{0},{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "abc1", "abc2"},
                { "cba", "abc"},
                { "abcddcba", "aabbccdd"},
                { "a1", "a2"},
                { "aaa1", "aaa2"}
        });
    }

    private final String one;
    private final String two;

    public SlickParamaterizedTest(final String one, final String two) {
        this.one = one;
        this.two = two;
    }

    @SlickMetaData(
            title = "SlickResultRule5",
            component = "bar5",
            feature = "bob5",
            automationKey = "blah5",
            automationId = "burp5",
            automationTool = "bike5",
            steps = {
                    @Step(step = "15{1}", expectation = "25"),
                    @Step(step = "35{0}", expectation = "45")
            }
    )
    @Test
    public void verifyRuleTest5() {
        Assert.assertTrue(true);
    }
}
