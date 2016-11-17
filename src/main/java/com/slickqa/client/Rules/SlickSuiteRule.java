package com.slickqa.client.Rules;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;
import com.slickqa.client.simple.impl.SlickSimpleClientConsoleImpl;
import com.slickqa.client.simple.impl.SlickSimpleClientImpl;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Keith on 11/15/16.
 */
public class SlickSuiteRule implements TestRule {

    protected final SlickSuite suite = SlickSuite.INSTANCE;
    protected Description description;

    @Override
    public Statement apply(Statement base, Description description) {
        this.description = description;
        beforeSuite(description);

        return base;
    }

    //Override this
    private void beforeSuite(Description description) {
        System.out.println("Before suite");
        suite.getClient(SlickSimpleClientConsoleImpl.class);
    }

    public void createTestRun(SlickTestRun slickTestRun) {
        suite.createTestRun(slickTestRun);
    }

    public void addResults() {
        ArrayList<SlickResult> slickResults = new ArrayList<>();

//        for (Class testClass : description.getTestClass().getClasses()) {
//            for (Method method: testClass.getMethods()) {
            for (Method method: description.getTestClass().getMethods()) {
                String name = method.getName();
                SlickMetaData metaData = method.getAnnotation(SlickMetaData.class);
                if (metaData == null) {
                    continue;
                }
                SlickTestCase testCase = this.buildSlickTestCaseFromMetaData(metaData);
                SlickResult result = this.buildResultFromMetaData(testCase);
                slickResults.add(result);
            }
//        }


        this.suite.addResults(slickResults);
    }

    public SlickSuite getSuite() {
        return this.suite;
    }

    private SlickResult buildResultFromMetaData(SlickTestCase testCase) {
        return SlickResult.builder()
                .addTestCase(testCase)
                .build();
    }

    private SlickTestCase buildSlickTestCaseFromMetaData(SlickMetaData metaData) {
        ArrayList<SlickStep> steps = new ArrayList<>();
        for (Step step : metaData.steps()) {
            steps.add(new SlickStep(step.step(), step.expectation()));
        }
        return SlickTestCase.builder()
            .addAutomationId(metaData.automationId())
            .addAutomationKey(metaData.automationKey())
            .addAutomationTool("Android")
            .addSteps(steps)
            .addComponent(metaData.component())
            .addFeature(metaData.feature())
            .addName(metaData.title())
            .build();
    }


    // Suite or Class Project name from System.getProperty("blah").equalsIgnoreCase("true")

}
