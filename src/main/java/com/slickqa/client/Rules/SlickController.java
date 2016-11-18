package com.slickqa.client.rules;

import com.google.common.collect.Lists;
import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;
import org.junit.Test;
import org.junit.runners.Suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
            loadProperties();
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
        ArrayList<Class<?>> suiteClasses = Lists.newArrayList(annotation.value());

        for (Method method: this.getTestMethods(suiteClasses)) {
            SlickMetaData metaData = method.getAnnotation(SlickMetaData.class);
            if (metaData == null) {
                continue;
            }
            slickResults.add(createResult(metaData));
        }

        this.updateResults(slickResults);
    }

    public void createMethodResult(SlickMetaData metaData) {
        // Creates result for a single Test Method
        // This is used when tests are not being run as part of a suite
        System.out.println("Creating Result for Method");
        SlickResult result = createResult(metaData);
        this.updateResults(Lists.newArrayList(result));
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

    public void addLogs(String resultId, ArrayList<SlickLog> logs) {
        client.addLogs(this.testRunId,
                resultId,
                logs);
    }

    private void createClient() {
        // Creating a specific client specified in the properties
        // This allows for different implementations of SlickSimpleClient, including test implementations
        try {
            String className = prop.getProperty("client_class_name");
            System.out.println("Initializing client : " + className);
            this.client = (SlickSimpleClient) ClassLoader.getSystemClassLoader().loadClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTestRun() {
        // Create new SlickTestRun from properties
        // Save off the TestRunId for later use
        SlickIdentity project = new SlickIdentity(prop.getProperty("project_name"), null);
        SlickIdentity release = new SlickIdentity(prop.getProperty("release_name", null), null);
        SlickIdentity build = new SlickIdentity(prop.getProperty("build_name", null), null);
        SlickIdentity testPlan = new SlickIdentity(prop.getProperty("test_plan_name", null), null);
        SlickIdentity testRun = new SlickIdentity(prop.getProperty("test_run_name", null), null);

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

    private void loadProperties() {
        //Getting properties from a System Property
        String configFile = System.getProperty("slick.config");
        InputStream input = null;
        try {
            input = new FileInputStream(new File(configFile));
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<Method> getTestMethods(ArrayList<Class<?>> klasses) {
        // Getting Test Methods from Klasses that are passed in
        ArrayList<Method> testMethods = Lists.newArrayList();

        for (Class klass : klasses) {
            for (Method method: klass.getMethods()) {
                if (method.getAnnotation(Test.class) != null) {
                    testMethods.add(method);
                }
            }
        }

        return testMethods;
    }

    private SlickResult createResult(SlickMetaData metaData) {
        // Building out the test case with the metadata and packaging it in a SlickResult
        ArrayList<SlickStep> steps = new ArrayList<>();
        for (Step step : metaData.steps()) {
            steps.add(new SlickStep(step.step(), step.expectation()));
        }

        SlickTestCase testCase = SlickTestCase.builder()
                .addAutomationId(metaData.automationId())
                .addAutomationKey(metaData.automationKey())
                .addAutomationTool("Android")
                .addSteps(steps)
                .addComponent(metaData.component())
                .addFeature(metaData.feature())
                .addName(metaData.title())
                .build();

        return SlickResult.builder().addTestCase(testCase).build();
    }
}
