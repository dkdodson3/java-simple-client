package com.slickqa.client.Rules;


/**
 * Created by Keith on 11/14/16.
 */
import com.slickqa.client.simple.definitions.SlickIdentity;
import com.slickqa.client.simple.definitions.SlickResult;
import com.slickqa.client.simple.definitions.SlickTestRun;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SlickSuiteRuleTestOne.class,
        SlickSuiteRuleTestTwo.class
})

public class SlickSuiteTest {
    @ClassRule
    public static SlickSuiteRule slickSuite = new SlickSuiteRule();

    @BeforeClass
    public static void setup() {
        SlickIdentity project = new SlickIdentity("two", null);
        SlickTestRun testRun = SlickTestRun.builder().addProject(project).build();
        slickSuite.createTestRun(testRun);
    }

    @AfterClass
    public static void after() {
        Set<String> keys = slickSuite.getSuite().getResultMap().keySet();
        for (String key : keys) {
            System.out.println(key);
        }
    }

}
