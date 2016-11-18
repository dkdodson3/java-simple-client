package com.slickqa.client.Rules;

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
    private SlickTestRun slickTestRun;
    private SlickSimpleClient client;

    public SlickController() {
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
        System.out.println("Creating Result for Method");
        ArrayList<SlickResult> slickResults = new ArrayList<>();
        slickResults.add(createResult(metaData));
        this.updateResults(slickResults);
    }

    public void updateResult(SlickResult result) {
        updateResults(Lists.newArrayList(result));
    }

    public void addLogs(String resultId, ArrayList<SlickLog> logs) {
        client.addLogs(getTestRunId(), resultId, logs);
    }

    private void createClient() {
        try {
            String className = prop.getProperty("client_class_name");
            System.out.println("Initializing client : " + className);
            this.client = (SlickSimpleClient) ClassLoader.getSystemClassLoader().loadClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTestRun() {
        // Create new SlickTestRun item
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

        if (this.slickTestRun == null)
        {
            try {

                this.slickTestRun = client.addTestRun(slickTestRun);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
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

    private void updateResults(ArrayList<SlickResult> resultList) {
        try {
            ArrayList<SlickResult> results = this.client.addResults(getTestRunId(), resultList);
            for (SlickResult result : results) {
                String key = (result.getTestCase().getAutomationKey().length() == 0) ? result.getTestCase().getAutomationKey() : result.getTestCase().getAutomationId();
                this.getResultMap().put(key, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTestRunId() {
        return this.slickTestRun.getTestRun().getId();
    }

    private SlickResult createResult(SlickMetaData metaData) {
        SlickTestCase testCase = this.buildSlickTestCaseFromMetaData(metaData);
        SlickResult slickResult = this.buildResultFromMetaData(testCase);
        return slickResult;
    }

    private ArrayList<Method> getTestMethods(ArrayList<Class<?>> suiteClasses) {
        ArrayList<Method> testMethods = Lists.newArrayList();

        // Get Test Methods
        for (Class suiteClass : suiteClasses) {
            for (Method method: suiteClass.getMethods()) {
                if (method.getAnnotation(Test.class) != null) {
                    testMethods.add(method);
                }
            }
        }

        return testMethods;
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
}
