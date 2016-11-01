package com.slickqa.client.simple.definitions;

import javax.ws.rs.client.Entity;
import java.util.List;

/**
 * Created by Keith on 10/25/16.
 */

public class TestRun {
    private final SlickIdentity project;
    private final SlickIdentity release;
    private final SlickIdentity build;
    private final SlickIdentity testPlan;
    private final SlickIdentity testRun;
    private final List<Result> results;

    private TestRun(SlickIdentity project,
                    SlickIdentity release,
                    SlickIdentity build,
                    SlickIdentity testPlan,
                    SlickIdentity testRun,
                    List<Result> results) {
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

    public List<Result> getResults() {
        return this.results;
    }

    public Entity toEntity(String mediaType) {
        return Entity.entity(this, mediaType);
    }

    public static TestRun fromEntity(Entity entity) {
        return (TestRun) entity.getEntity();
    }

    public static TestRunBuilder builder() {
        return new TestRunBuilder();
    }

}