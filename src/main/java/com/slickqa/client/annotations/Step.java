package com.slickqa.client.annotations;

/**
 * Created by Keith on 11/11/16.
 * Used to create steps for SlickMetaData
 */
public @interface Step {
    public String step();
    public String expectation() default "NA";
}
