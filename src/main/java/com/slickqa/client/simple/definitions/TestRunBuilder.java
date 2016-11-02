package com.slickqa.client.simple.definitions;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TestRunBuilder {
    private SlickIdentity project;
    private SlickIdentity release;
    private SlickIdentity build;
    private SlickIdentity testPlan;
    private SlickIdentity testRun;
    private List<SlickResult> results;

    public TestRunBuilder addProject(@NonNull SlickIdentity project) {
        this.project = project;
        return this;
    }

    public TestRunBuilder addRelease(SlickIdentity release) {
        this.release = release;
        return this;
    }

    public TestRunBuilder addBuild(SlickIdentity build) {
        this.build = build;
        return this;
    }

    public TestRunBuilder addTestPlan(SlickIdentity testPlan) {
        this.testPlan = testPlan;
        return this;
    }

    public TestRunBuilder addTestRun(SlickIdentity testRun) {

        this.testRun = testRun;
        return this;
    }

    public TestRunBuilder addResult(SlickResult result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }

        this.results.add(result);
        return this;
    }

    public TestRunBuilder addResults(List<SlickResult> results) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }

        this.results.addAll(results);
        return this;
    }

    public SlickTestRun build() {
        return new SlickTestRun(project, release, build, testPlan, testRun, results);
    }
}