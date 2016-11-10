package com.slickqa.client.simple.definitions;

import java.util.ArrayList;
import java.util.List;

public class SlickTestRunBuilder {
    private Long started;
    private Long finished;
    private SlickIdentity project;
    private SlickIdentity release;
    private SlickIdentity build;
    private SlickIdentity testPlan;
    private SlickIdentity testRun;
    private List<SlickResult> results;

    public SlickTestRunBuilder addStarted(Long started) {
        this.started = started;
        return this;
    }

    public SlickTestRunBuilder addFinished(Long finished) {
        this.finished = finished;
        return this;
    }

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
        this.results = results;
        return this;
    }

    public SlickTestRun build() {
        return new SlickTestRun(started, finished, project, release, build, testPlan, testRun, results);
    }
}