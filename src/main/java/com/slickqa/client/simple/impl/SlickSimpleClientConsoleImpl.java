package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Keith on 11/15/16.
 */
public class SlickSimpleClientConsoleImpl implements SlickSimpleClient {
    @Override
    public SlickTestRun addTestRun(SlickTestRun slickTestRun) throws IOException {
        System.out.println("TestRun");
        SlickIdentity testRun = new SlickIdentity("bar", "1234");
        SlickTestRun retTestRun = SlickTestRun.builder().addProject(slickTestRun.getProject()).addTestRun(testRun).build();
        System.out.println(retTestRun.toObjectNode().toString());

        return retTestRun;
    }

    @Override
    public ArrayList<SlickResult> addResults(String testRunId, ArrayList<SlickResult> results) throws IOException {
        System.out.println("Results");
        for (int i = 0; i < results.size(); i++) {
            SlickResult result = results.get(i);
            System.out.println("[ " + i + " ]");
            System.out.println("Result Id: " + result.getId());
            System.out.println("TestCase Key: " + result.getTestCase().getAutomationKey());
            System.out.println("TestCase Id: " + result.getTestCase().getAutomationId());
            System.out.println("TestCase Name: " + result.getTestCase().getName());
            System.out.println();
            result.setId("9876");
        }

        return results;
    }

    @Override
    public void addLogs(String testRunId, String resultId, ArrayList<SlickLog> slickLogs) {
        System.out.println("Logs");
        for (SlickLog log: slickLogs) {
            System.out.println(log.toObjectNode().textValue());
        }
    }

    @Override
    public ArrayList<SlickFile> addFiles(String testRunId, String resultId, ArrayList<SlickFile> slickFiles) throws IOException {
        System.out.println("Files");
        for (SlickFile slickFile: slickFiles) {
            slickFile.getIdentity().setId("9876");
            System.out.println(slickFile.getIdentity().toObjectNode().textValue());
        }

        return slickFiles;
    }
}
