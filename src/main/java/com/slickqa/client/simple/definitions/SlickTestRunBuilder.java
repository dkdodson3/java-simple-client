package com.slickqa.client.simple.definitions;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SlickTestRunBuilder {
    private SlickIdentity project;
    private SlickIdentity release;
    private SlickIdentity build;
    private SlickIdentity testPlan;
    private SlickIdentity testRun;
    private List<SlickResult> results;

    public SlickTestRunBuilder addProject(SlickIdentity project) {
        this.project = project;
        return this;
    }

    public SlickTestRunBuilder addRelease(SlickIdentity release) {
        this.release = release;
        return this;
    }

    public SlickTestRunBuilder addBuild(SlickIdentity build) {
        this.build = build;
        return this;
    }

    public SlickTestRunBuilder addTestPlan(SlickIdentity testPlan) {
        this.testPlan = testPlan;
        return this;
    }

    public SlickTestRunBuilder addTestRun(SlickIdentity testRun) {

        this.testRun = testRun;
        return this;
    }

    public SlickTestRunBuilder addResult(SlickResult result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }

        this.results.add(result);
        return this;
    }

    public SlickTestRunBuilder addResults(List<SlickResult> results) {
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