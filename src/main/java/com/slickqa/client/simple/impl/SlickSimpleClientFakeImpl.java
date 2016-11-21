package com.slickqa.client.simple.impl;

import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Keith on 11/15/16.
 */
public class SlickSimpleClientFakeImpl implements SlickSimpleClient {
    @Override
    public SlickTestRun addTestRun(SlickTestRun slickTestRun) throws IOException {
        System.out.println("TestRun");
        SlickIdentity project = slickTestRun.getProject();
        if  (project.getId() == null) {
            project.setId(randomNumber(10));
        }

        SlickIdentity release = slickTestRun.getRelease();
        if  (release.getId() == null && release.getName() != null) {
            release.setId(randomNumber(10));
        }

        SlickIdentity build = slickTestRun.getBuild();
        if  (build.getId() == null && build.getName() != null) {
            build.setId(randomNumber(10));
        }

        SlickIdentity testPlan = slickTestRun.getTestPlan();
        if  (testPlan.getId() == null && testPlan.getName() != null) {
            testPlan.setId(randomNumber(10));
        }

        SlickIdentity testRun = slickTestRun.getTestRun();
        if  (testRun.getId() == null && testRun.getName() != null) {
            testRun.setId(randomNumber(10));
        }

        SlickTestRun retSlickTestRun = SlickTestRun.builder()
                .addProject(project)
                .addRelease(release)
                .addBuild(build)
                .addTestPlan(testPlan)
                .addTestRun(testRun)
                .build();
        System.out.println(retSlickTestRun.toObjectNode().toString());

        return retSlickTestRun;
    }

    @Override
    public ArrayList<SlickResult> addResults(String testRunId, ArrayList<SlickResult> results) throws IOException {
        System.out.println("Results");
        ArrayList<SlickResult> retResults = new ArrayList<>();
        for (SlickResult result: results) {
            if (result.getId() == null) {
                result.setId(randomNumber(10));
            }
            System.out.println(result.toObjectNode().toString());
            retResults.add(result);
        }

        return retResults;
    }

    @Override
    public void addLogs(String testRunId, String resultId, ArrayList<SlickLog> slickLogs) {
        if (slickLogs.size() > 0) {
            System.out.println("Logs");
            for (SlickLog log: slickLogs) {
                System.out.println(log.toObjectNode().toString());
            }
        }
    }

    @Override
    public ArrayList<SlickFile> addFiles(String testRunId, String resultId, ArrayList<SlickFile> slickFiles) throws IOException {
        System.out.println("Files");
        for (SlickFile slickFile: slickFiles) {
            slickFile.getIdentity().setId(randomNumber(10));
            System.out.println("FilePath: " + slickFile.getFilePath());
            System.out.println(slickFile.getIdentity().toObjectNode().toString());
        }

        return slickFiles;
    }

    private String randomNumber(int num) {
        Random randomGenerator = new Random();
        String value = "";
        for (int i = 0; i < num; ++i){
            Integer randomInt = randomGenerator.nextInt(9);
            if (randomInt == 0 && value.length() == 0) {
                randomInt = 1;
            }

            value = value + randomInt.toString();
        }

        return value;
    }






}
