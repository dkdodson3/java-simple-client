package com.slickqa.client.simple.definitions;

import java.util.ArrayList;
import java.util.List;

public class TestRunBuilder {
    private SlickIdentity project;
    private SlickIdentity release;
    private SlickIdentity build;
    private SlickIdentity testPlan;
    private SlickIdentity testRun;
    private List<Result> results;

    public TestRunBuilder addProject(SlickIdentity project) {
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

    public TestRunBuilder addResult(Result result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }

        this.results.add(result);
        return this;
    }

    public TestRunBuilder addResults(List<Result> results) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }

        this.results.addAll(results);
        return this;
    }

    public TestRun build() {
        return TestRun.createTestRun(project, release, build, testPlan, testRun, results);
    }
}