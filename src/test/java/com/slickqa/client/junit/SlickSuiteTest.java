package com.slickqa.client.junit;


/**
 * Created by Keith on 11/14/16.
 */
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(SlickSuite.class)
@SlickSuite.SuiteClasses({
        SlickSuiteRuleTestOne.class,
        SlickSuiteRuleTestTwo.class,
        SlickParamaterizedTest.class
})
public class SlickSuiteTest {
    @BeforeClass
    public static void setup() {
    }
}
