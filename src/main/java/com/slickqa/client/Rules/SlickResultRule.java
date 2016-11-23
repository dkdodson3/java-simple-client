package com.slickqa.client.rules;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.simple.definitions.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Keith on 11/14/16.
 */
public class SlickResultRule extends TestWatcher {
    public final SlickController controller = SlickController.INSTANCE;
    protected String logType = "";
    protected ArrayList<SlickLog> logs = Lists.newArrayList();
    protected File tempDir;
    protected SlickMetaData metaData;
    protected SlickResult result;

    @Override
    protected void starting(Description description) {
        if (description.isTest()) {
            this.metaData = description.getAnnotation(SlickMetaData.class);
            String parameter = this.getParameters(description);
            String key;
            if (parameter != null) {
                key = (this.metaData.automationKey().length() == 0) ? this.metaData.automationKey() + "_" + parameter : metaData.automationId() + "_" + parameter;
            } else {
                key = (this.metaData.automationKey().length() == 0) ? this.metaData.automationKey() : metaData.automationId();
            }

            this.result = this.controller.getResultMap().get(key);

            // Update result with parameter if needed
            // Create result with parameters if no result
            if (getResult() != null) {
                if (parameter != null) {
                    this.controller.updateResult(getResult(), parameter);
                }
            } else {
                this.controller.createMethodResult(metaData, parameter);
            }

            this.result = this.controller.getResultMap().get(key);

            this.tempDir = Files.createTempDir();
            this.tempDir.deleteOnExit();
        }
    }

    protected String getParameters(Description description) {
        if (description.getTestClass().getAnnotation(RunWith.class) != null && description.getTestClass().getAnnotation(RunWith.class).value().getName().equals("org.junit.runners.Parameterized")) {
            String name = description.getMethodName();
            return name.substring(name.indexOf('['), name.length());//.replace("[", "").replace("]","");
        }

        return null;
    }

    @Override
    protected void finished(Description description) {
        this.controller.updateResult(this.result, null);
        this.controller.addLogs(this.result.getId(), this.logs);
        ArrayList<SlickFile> files = new ArrayList<>();
        for (File file: this.getTempDir().listFiles()) {

            if (file.isFile()) {
                SlickFile slickFile = SlickFile.builder()
                        .addFilePath(file.getAbsolutePath())
                        .addIdentity(new SlickIdentity(file.getName(), null))
                        .build();
                files.add(slickFile);

            }
        }
        this.controller.addFiles(this.getResult().getId(), files);
    }

    @Override
    protected void succeeded(Description description) {
        this.updateResultStatus(SlickResultStatus.PASS);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        this.updateResultStatus(SlickResultStatus.FAIL);
        String message = "Failed: " + this.getMetaData().title();

        StringBuilder stacktrace = new StringBuilder();
        for (StackTraceElement traceElement : e.getStackTrace()) {
            stacktrace.append(traceElement.toString());
            stacktrace.append("\n");
        }

        this.logIt(
                message,
                "ERROR",
                this.logType,
                stacktrace.toString(),
                e.getStackTrace().getClass().getName(),
                e.getMessage());
        e.printStackTrace();
    }

    public File getTempDir() {
        return this.tempDir;
    }

    public SlickMetaData getMetaData() { return this.metaData; }

    public SlickResult getResult() {
        return this.result;
    }

    public SlickTestCase getTestCase() {
        return this.getResult().getTestCase();
    }

    public void updateResult(SlickResult result) {
        this.result = result;
    }

    public void updateTestCase(SlickTestCase testCase) {
        this.getResult().setTestCase(testCase);
    }

    public void updateResultStatus(SlickResultStatus status) {
        this.getResult().setStatus(status);
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void info(String message) {
        this.logIt(message, "INFO");
    }

    public void error(String message) {
        this.logIt(message, "ERROR");
    }

    private void logIt(String message, String level) {
        this.logIt(message, level, this.logType, null, null, null);
    }

    private void logIt(String message, String level, String loggerName, String stackTrace, String className, String exceptionMessage) {
        SlickLog log = SlickLog.builder()
                .addMessage(message)
                .addEntryTime(System.currentTimeMillis())
                .addLevel(level)
                .addLoggerName(loggerName)
                .addExceptionStackTrace(stackTrace)
                .addExceptionClassName(className)
                .addExceptionMessage(exceptionMessage)
                .build();
        this.logs.add(log);
    }



}
