package com.slickqa.client.rules;

import com.google.common.collect.Lists;
import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Keith on 11/16/16.
 */
public class SlickController {

    public static final SlickController INSTANCE = new SlickController();

    private final AtomicBoolean initialized = new AtomicBoolean();
    private ConcurrentHashMap<String, SlickResult> resultMap = new ConcurrentHashMap<>();
    private Properties prop = new Properties();
    private SlickSimpleClient client;
    private String testRunId;

    public SlickController() {
        // Starting everything needed for the controller
        // Only allowing this to be run once and then used in multiple places
        if (!this.initialized.get()) {
            createClient();
            createTestRun();
            this.initialized.set(true);
        }
    }

    public ConcurrentHashMap<String, SlickResult> getResultMap() {
        return this.resultMap;
    }

    public void createSuiteResults(Class suiteClass) {
        // Find and create results for all the Test Methods in the SuiteClass
        ArrayList<SlickResult> slickResults = new ArrayList<>();
        Suite.SuiteClasses annotation = (Suite.SuiteClasses) suiteClass.getAnnotation(Suite.SuiteClasses.class);
        Class<?>[] values = annotation.value();
        ArrayList<Class<?>> suiteClasses = new ArrayList<>();
        for (Class<?> klass : values) {
            suiteClasses.add(klass);
        }

        for (Method method: this.getTestMethods(suiteClasses)) {
            SlickMetaData metaData = method.getAnnotation(SlickMetaData.class);
            if (metaData == null) {
                continue;
            }
            slickResults.add(createResult(metaData, null));
        }

        this.updateResults(slickResults);
    }

    public void createMethodResult(SlickMetaData metaData, String parameter) {
        // Creates result for a single Test Method
        // This is used when tests are not being run as part of a controller
        System.out.println("Creating Result for Method");
        this.updateResult(createResult(metaData, parameter), parameter);
    }

    public void updateResults(ArrayList<SlickResult> resultList) {
        // Sending the results to the client
        // Adding returned results to the ResultMap by either the AutomationKey or the AutomationId
        try {
            String testRunId = this.testRunId;
            ArrayList<SlickResult> results = this.client.addResults(testRunId, resultList);
            for (SlickResult result : results) {
                String key = (result.getTestCase().getAutomationKey().length() == 0) ? result.getTestCase().getAutomationKey() : result.getTestCase().getAutomationId();
                this.getResultMap().put(key, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateResult(SlickResult result, String parameter) {
        ArrayList<SlickResult> results = new ArrayList<>();
        if (parameter != null) {
            this.parametizeTestCase(result, parameter);
        }
        results.add(result);
        this.updateResults(results);
    }

    public void addLogs(String resultId, ArrayList<SlickLog> logs) {
        client.addLogs(this.testRunId,
                resultId,
                logs);
    }

    public void addFiles(String resultId, ArrayList<SlickFile> files) {
        try {
            client.addFiles(this.testRunId,
                    resultId,
                    files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createClient() {
        // Creating a specific client specified in the properties
        // This allows for different implementations of SlickSimpleClient, including test implementations
        String className = "com.slickqa.client.simple.impl.SlickSimpleClientFakeImpl";
        if (System.getProperty("slick.project") != null) {
            // If we have the slick_project specified we will use the specified slick_impl
            // If no slick_impl is specified then we will use the default slick_impl
            className = System.getProperty("slick.impl",
                    "com.slickqa.client.simple.impl.SlickSimpleClientImpl");
        }

        try {
            this.client = (SlickSimpleClient) this.getClass().getClassLoader().loadClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTestRun() {
        // Create new SlickTestRun from properties
        // Save off the TestRunId for later use
        SlickIdentity project = new SlickIdentity(System.getProperty("slick.project", "fakeProject"), null);
        SlickIdentity release = new SlickIdentity(System.getProperty("slick.release", null), null);
        SlickIdentity build = new SlickIdentity(System.getProperty("slick.build", null), null);
        SlickIdentity testPlan = new SlickIdentity(System.getProperty("slick.testplan", null), null);
        SlickIdentity testRun = new SlickIdentity(System.getProperty("slick.testrun", null), null);

        SlickTestRun slickTestRun = SlickTestRun.builder()
                .addProject(project)
                .addRelease(release)
                .addBuild(build)
                .addTestPlan(testPlan)
                .addTestRun(testRun)
                .build();

        try {
            SlickTestRun retTestRun = client.addTestRun(slickTestRun);
            this.testRunId = retTestRun.getTestRun().getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Method> getTestMethods(ArrayList<Class<?>> klasses) {
        // Getting Test Methods from Klasses that are passed in
        ArrayList<Method> testMethods = new ArrayList<>();

        for (Class klass : klasses) {
            for (Method method: klass.getMethods()) {
                if (method.getAnnotation(Test.class) != null) {
                    testMethods.add(method);
                }
            }
        }

        return testMethods;
    }

    private SlickResult createResult(SlickMetaData metaData, String parameter) {
        // Building out the test case with the metadata and packaging it in a SlickResult
        SlickTestCase testCase;
        ArrayList<SlickStep> steps = new ArrayList<>();
        for (Step step : metaData.steps()) {
            steps.add(new SlickStep(step.step(), step.expectation()));
        }

        testCase = SlickTestCase.builder()
                .addAutomationId(metaData.automationId())
                .addAutomationKey(metaData.automationKey())
                .addAutomationTool(metaData.automationTool())
                .addSteps(steps)
                .addComponent(metaData.component())
                .addFeature(metaData.feature())
                .addName(metaData.title())
                .build();

        SlickResult result = SlickResult.builder().addTestCase(testCase).build();

        return result;
    }

    protected void parametizeTestCase(SlickResult result, String parameter) {
        // Putting the parameters into the steps
        List<String> params = Arrays.asList(parameter.split(","));
        SlickTestCase testCase = result.getTestCase();
        List<SlickStep> steps = testCase.getSteps();
        List<SlickStep> newSteps = new ArrayList<>();
        for (SlickStep step: steps) {
            for (int i = 0; i < params.size(); i++) {
                step.setName(step.getName().replace("{" + i + "}", params.get(i)));
                step.setExpectedResults(step.getExpectedResults().replace("{" + i + "}", params.get(i)));
            }
            newSteps.add(step);
        }

        // Updating the pertinent Test Case Data
        String name = testCase.getName() + " : " + parameter;
        String automationId = testCase.getAutomationId() + "_" + parameter;
        String automationKey = testCase.getAutomationKey() + "_" + parameter;

        // Rebuilding the Test Case and adding it to the result
        SlickTestCase newTestCase = SlickTestCase.builder()
                .addName(name)
                .addAutomationId(automationId)
                .addAutomationKey(automationKey)
                .addAutomationTool(testCase.getAutomationTool())
                .addSteps(newSteps)
                .addComponent(testCase.getComponent())
                .addFeature(testCase.getFeature())
                .build();

        result.setTestCase(newTestCase);
    }


}
