package com.slickqa.client.simple.definitions;

import com.google.common.base.Strings;
import lombok.NonNull;

import javax.ws.rs.client.Entity;
import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class SlickTestRun {
    public final static String MESSAGE_PROJECT_NAME_OR_ID_EMPTY = "Project Name and Id cannot both be empty";

    private final SlickIdentity project;
    private final SlickIdentity release;
    private final SlickIdentity build;
    private final SlickIdentity testPlan;
    private final SlickIdentity testRun;
    private final List<SlickResult> results;

    public SlickTestRun(@NonNull SlickIdentity project,
                        SlickIdentity release,
                        SlickIdentity build,
                        SlickIdentity testPlan,
                        SlickIdentity testRun,
                        @NonNull List<SlickResult> results) {

        if (Strings.isNullOrEmpty(project.getId()) && Strings.isNullOrEmpty(project.getName())) {
            throw new IllegalArgumentException(MESSAGE_PROJECT_NAME_OR_ID_EMPTY);
        }

        this.project = project;
        this.release = release;
        this.build = build;
        this.testPlan = testPlan;
        this.testRun = testRun;
        this.results = results;
    }

    public SlickIdentity getProject() {
        return this.project;
    }

    public SlickIdentity getRelease() {
        return this.release;
    }

    public SlickIdentity getBuild() {
        return this.build;
    }

    public SlickIdentity getTestPlan() {
        return this.testPlan;
    }

    public SlickIdentity getTestRun() {
        return this.testRun;
    }

    public List<SlickResult> getResults() {
        return this.results;
    }

    public static SlickTestRunBuilder builder() {
        return new SlickTestRunBuilder();
    }

}