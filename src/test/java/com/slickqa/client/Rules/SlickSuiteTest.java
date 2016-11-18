package com.slickqa.client.Rules;


/**
 * Created by Keith on 11/14/16.
 */
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SlickSuiteRuleTestOne.class,
        SlickSuiteRuleTestTwo.class
})
public class SlickSuiteTest {
    @BeforeClass
    public static void setup() {
        System.out.println("One Setup to Rule them All");
        SlickController suite = SlickController.INSTANCE;
        suite.createSuiteResults(SlickSuiteTest.class);
    }

}
