package com.slickqa.client.annotations;

import com.slickqa.client.simple.definitions.SlickIdentity;
import com.slickqa.client.simple.definitions.SlickResult;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by Keith on 11/14/16.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.PACKAGE )
public @interface TestRun {
    String value = "";

    public String projectName();
    public String projectId() default value;
    public String releaseName() default value;
    public String releaseId() default value;
    public String buildName() default value;
    public String buildId() default value;
    public String testPlanName() default value;
    public String testPlanId() default value;
    public String testRunName() default value;
    public String testRunId() default value;
}
