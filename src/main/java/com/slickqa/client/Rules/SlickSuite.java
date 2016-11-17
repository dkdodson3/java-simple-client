package com.slickqa.client.Rules;

import com.google.common.collect.Lists;
import com.slickqa.client.simple.SlickSimpleClient;
import com.slickqa.client.simple.definitions.SlickResult;
import com.slickqa.client.simple.definitions.SlickTestRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Keith on 11/16/16.
 */
public class SlickSuite {

    public static final SlickSuite INSTANCE = new SlickSuite();

    private final AtomicBoolean initialized = new AtomicBoolean();

    private ConcurrentHashMap<String, SlickResult> resultMap = new ConcurrentHashMap<>();
    private SlickTestRun slickTestRun = null;
    private SlickSimpleClient client;


    public SlickSimpleClient getClient(Class className) {
        if (!this.initialized.get()) {
            try {
                System.out.println("Initializing Client");
                this.client = (SlickSimpleClient) className.newInstance();
                this.initialized.set(true);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return this.client;
    }

    public void createTestRun(SlickTestRun slickTestRun) {
        if (this.slickTestRun == null)
        {
            try {

                this.slickTestRun = client.addTestRun(slickTestRun);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addResults(ArrayList<SlickResult> resultList) {
        String testRunId = this.slickTestRun.getTestRun().getId();
        try {

            ArrayList<SlickResult> results = this.client.addResults(testRunId, resultList);
            for (SlickResult result : results) {
                String key = (result.getTestCase().getAutomationKey().length() == 0) ? result.getTestCase().getAutomationKey() : result.getTestCase().getAutomationId();
                this.getResultMap().put(key, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addResult(SlickResult result) {
        addResults(Lists.newArrayList(result));
    }

    public ConcurrentHashMap<String, SlickResult> getResultMap() {
        return this.resultMap;
    }


}
