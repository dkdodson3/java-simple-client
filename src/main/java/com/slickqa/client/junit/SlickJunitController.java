package com.slickqa.client.junit;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.annotations.Step;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.io.IOException;
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
public class SlickJunitController {
    public static SlickJunitController INSTANCE;

    public static SlickJunitController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlickJunitController();
        }
        return INSTANCE;
    }

    protected final AtomicBoolean initialized = new AtomicBoolean();
    protected ConcurrentHashMap<String, SlickResult> resultMap = new ConcurrentHashMap<>();
    protected SlickSimpleClient client;
    protected String testRunId;
    protected SlickTestRun testRun;
    protected Properties properties;

    public SlickJunitController() {
        // Starting everything needed for the controller
        // Only allowing this to be run once and then used in multiple places
        if (!this.initialized.get()) {

            System.out.println("Starting Controller");
            createClient();
            createTestRun();
            this.initialized.set(true);
        }
    }

    public ConcurrentHashMap<String, SlickResult> getResultMap() {
        return this.resultMap;
    }

    public void createMethodResult(SlickMetaData metaData, String parameter) {
        // Creates result for a single Test Method
        // This is used when tests are not being run as part of a controller
        System.out.println("Creating Result for Method");
        this.updateResult(createResult(metaData, parameter));
    }

    public void createSuiteResults(List<Description> children) {
        ArrayList<SlickResult> slickResults = new ArrayList<>();

        for (Description description : children) {
            for (Method method : description.getTestClass().getMethods()) {
                if (method.getAnnotation(Test.class) != null) {
                    SlickMetaData metaData = method.getAnnotation(SlickMetaData.class);

                    if (metaData == null) {
                        continue;
                    }

                    List<String> params = getParams(description);
                    if (params.size() < 1) {
                        slickResults.add(this.createResult(metaData, null));
                    } else {
                        for (String param : params) {
                            slickResults.add(this.createResult(metaData, param));
                        }
                    }
                }
            }
        }

        // Need to fix to update tests with params now...
        this.updateResults(slickResults);
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

    public void updateResult(SlickResult result) {
        ArrayList<SlickResult> results = new ArrayList<>();
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

    protected Properties getProperties() {
        if (this.properties == null) {
            this.properties = System.getProperties();
        }
        return this.properties;
    }

    protected void createClient() {
        Properties props = getProperties();
        // Creating a specific client specified in the properties
        // This allows for different implementations of SlickSimpleClient, including test implementations
        String className = "com.slickqa.client.simple.impl.SlickSimpleClientFakeImpl";
        if (props.getProperty("slick.project") != null) {
            // If we have the slick_project specified we will use the specified slick_impl
            // If no slick_impl is specified then we will use the default slick_impl
            className = props.getProperty("slick.impl",
                    "com.slickqa.client.simple.impl.SlickSimpleClientImpl");
        }

        try {
            this.client = (SlickSimpleClient) this.getClass().getClassLoader().loadClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void createTestRun() {
        Properties props = getProperties();
        // Create new SlickTestRun from properties
        // Save off the TestRunId for later use
        if (this.testRun == null) {
            SlickIdentity project = new SlickIdentity(props.getProperty("slick.project", "fakeProject"), null);
            SlickIdentity release = new SlickIdentity(props.getProperty("slick.release", null), null);
            SlickIdentity build = new SlickIdentity(props.getProperty("slick.build", null), null);
            SlickIdentity testPlan = new SlickIdentity(props.getProperty("slick.testplan", null), null);
            SlickIdentity testRun = new SlickIdentity(props.getProperty("slick.testrun", null), null);

            SlickTestRun slickTestRun = SlickTestRun.builder()
                    .addProject(project)
                    .addRelease(release)
                    .addBuild(build)
                    .addTestPlan(testPlan)
                    .addTestRun(testRun)
                    .build();

            try {
                this.testRun = client.addTestRun(slickTestRun);
                this.testRunId = this.testRun.getTestRun().getId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private ArrayList<Method> getTestMethods(ArrayList<Class<?>> klasses) {
//        // Getting Test Methods from Klasses that are passed in
//        ArrayList<Method> testMethods = new ArrayList<>();
//
//        for (Class klass : klasses) {
//            for (Method method: klass.getMethods()) {
//                if (method.getAnnotation(Test.class) != null) {
//                    testMethods.add(method);
//                }
//            }
//        }
//
//        return testMethods;
//    }

    public SlickResult createResult(SlickMetaData metaData, String parameter) {
        // Building out the test case with the metadata and packaging it in a SlickResult
        SlickTestCase testCase;
        ArrayList<SlickStep> stepList = new ArrayList<>();
        Step[] steps = metaData.steps();

        String name = metaData.title();
        String automationId = metaData.automationId();
        String automationKey = metaData.automationKey();

        if (parameter != null) {
            name = name + " : " + parameter;
            automationId = automationId + "_" + parameter;
            automationKey = automationKey + "_" + parameter;

            parameter = parameter.replace("[", "").replace("]", "");
            List<String> params = Arrays.asList(parameter.split(","));
            for (Step step: steps) {
                String stepText = step.step();
                String expectationText = step.expectation();
                for (int i = 0; i < params.size(); i++) {
                    stepText = stepText.replace("{" + i + "}", params.get(i));
                    expectationText = expectationText.replace("{" + i + "}", params.get(i));
                }

                stepList.add(new SlickStep(stepText, expectationText));
            }
        } else {
            for (Step step : steps) {
                stepList.add(new SlickStep(step.step(), step.expectation()));
            }
        }

        testCase = SlickTestCase.builder()
                .addAutomationId(automationId)
                .addAutomationKey(automationKey)
                .addAutomationTool(metaData.automationTool())
                .addSteps(stepList)
                .addComponent(metaData.component())
                .addFeature(metaData.feature())
                .addName(name)
                .build();

        return SlickResult.builder().addTestCase(testCase).build();
    }

    private List<String> getParams(Description description) {
        List<String> testParams = new ArrayList<>();
        if (description.getTestClass().getAnnotation(RunWith.class) != null &&
                description.getTestClass().getAnnotation(RunWith.class).value().getName().equals("org.junit.runners.Parameterized")) {

            for (Description item: description.getChildren()) {
                testParams.add(item.getDisplayName());
            }
        }

        return testParams;
    }
}
