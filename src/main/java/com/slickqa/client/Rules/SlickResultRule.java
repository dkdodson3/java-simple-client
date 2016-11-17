package com.slickqa.client.Rules;

import com.slickqa.client.annotations.SlickMetaData;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.SlickResult;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by Keith on 11/14/16.
 */
public class SlickResultRule extends TestWatcher {
    private volatile SlickMetaData metaData;
    private volatile SlickSimpleClient client;
    private volatile SlickResult result;
    private volatile Description description;

    @Override
    protected void starting(Description description) {
        this.description = description;
        if (description.isTest()) {
            metaData = description.getAnnotation(SlickMetaData.class);
        }


        //Create Result
    }

    public SlickMetaData getMetaData() {
        return metaData;
    }

    // Pass Client so we can update the result
}
