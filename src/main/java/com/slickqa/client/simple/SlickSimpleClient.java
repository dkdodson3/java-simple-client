package com.slickqa.client.simple;

import com.slickqa.client.simple.definitions.Result;
import com.slickqa.client.simple.definitions.SlickFile;
import com.slickqa.client.simple.definitions.SlickLog;
import com.slickqa.client.simple.definitions.TestRun;

import java.util.ArrayList;

/**
 * Created by Keith on 10/26/16.
 */
public interface SlickSimpleClient {

    public TestRun createTestRun(TestRun testRun);

//    public ArrayList<Result> sendResults(String testRunId, ArrayList<Result> results);
//
//    public void sendLog(String resultId, SlickLog slickLog);
//
//    public void sendFile(String resultId, SlickFile slickFile);
}
