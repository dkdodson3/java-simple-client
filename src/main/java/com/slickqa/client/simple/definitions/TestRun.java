package com.slickqa.client.simple.definitions;

import lombok.*;
import java.util.List;


/**
 * Created by Keith on 10/25/16.
 */
@Builder
@Getter
public class TestRun {
    @NonNull private SlickIdentity project;
    private SlickIdentity release;
    private SlickIdentity build;
    private SlickIdentity testPlan;
    private SlickIdentity testRun;
    @Singular private List<Result> results;
}
