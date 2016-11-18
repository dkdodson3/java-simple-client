package com.slickqa.client.rules;

import com.google.common.collect.Lists;
import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.simple.definitions.SlickLog;
import com.slickqa.client.simple.definitions.SlickResult;
import com.slickqa.client.simple.definitions.SlickResultStatus;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.ArrayList;

/**
 * Created by Keith on 11/14/16.
 */
public class SlickResultRule extends TestWatcher {
    public final SlickController suite = SlickController.INSTANCE;

    private ArrayList<SlickLog> logs = Lists.newArrayList();
    private String logType = "android";
    private SlickMetaData metaData;
    private SlickResult result;
    private String key;

    @Override
    protected void starting(Description description) {
        if (description.isTest()) {
            this.metaData = description.getAnnotation(SlickMetaData.class);
            this.key = (metaData.automationKey().length() == 0) ? metaData.automationKey() : metaData.automationId();

            // Creating the Result if it is not in the ResultMap
            if (this.suite.getResultMap().get(this.key) == null) {
                this.suite.createMethodResult(this.metaData);
            }
            this.result = this.suite.getResultMap().get(this.key);
        }
    }

    @Override
    protected void succeeded(Description description) {
        System.out.println("Test Succeeded: " + description.getMethodName());
        this.updateResult(SlickResultStatus.PASS);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("Test Failed: " + description.getMethodName());
        this.updateResult(SlickResultStatus.FAIL);
        String message = "Failed in method: " + description.getMethodName();
        this.logIt(
                message,
                "ERROR",
                this.logType,
                e.getStackTrace().toString(),
                e.getStackTrace().getClass().getName(),
                e.getMessage());
    }

    @Override
    protected void finished(Description description) {
        System.out.println("Finished: " + description.getMethodName());
        this.suite.addLogs(this.result.getId(), this.logs);
        // Files
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

    private void updateResult(SlickResultStatus status) {
        System.out.println("Updating the Result: " + status.toString());
        this.result.setStatus(status);
        this.suite.updateResults(Lists.newArrayList(this.result));
    }

}
