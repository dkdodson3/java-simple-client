package com.slickqa.client.rules;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.simple.definitions.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Keith on 11/14/16.
 */
public class SlickResultRule extends TestWatcher {
    public final SlickController controller = SlickController.INSTANCE;

    private ArrayList<SlickLog> logs = Lists.newArrayList();
    private String logType = "android";
    private SlickResult result;
    private File tempDir;

    @Override
    protected void starting(Description description) {
        if (description.isTest()) {
            SlickMetaData metaData = description.getAnnotation(SlickMetaData.class);
            String key = (metaData.automationKey().length() == 0) ? metaData.automationKey() : metaData.automationId();

            // Creating the Result if it is not in the ResultMap
            if (this.controller.getResultMap().get(key) == null) {
                this.controller.createMethodResult(metaData);
            }
            this.result = this.controller.getResultMap().get(key);

            this.tempDir = Files.createTempDir();
            tempDir.deleteOnExit();
        }
    }

    @Override
    protected void finished(Description description) {
        System.out.println("Finished: " + description.getMethodName());
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
        this.controller.addFiles(this.result.getId(), files);
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

    public File getTempDir() {
        return this.tempDir;
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

    private void updateResult(SlickResultStatus status) {
        System.out.println("Updating the Result: " + status.toString());
        this.result.setStatus(status);
        this.controller.updateResult(this.result);
    }

}
