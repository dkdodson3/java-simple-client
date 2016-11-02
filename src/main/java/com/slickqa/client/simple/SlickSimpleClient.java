package com.slickqa.client.simple;

import com.slickqa.client.simple.definitions.SlickLog;
import com.slickqa.client.simple.definitions.SlickTestRun;

/**
 * Created by Keith on 10/26/16.
 */
public interface SlickSimpleClient {

    public SlickTestRun addTestRun(SlickTestRun testRun);

//    public ArrayList<SlickResult> addResults(String testRunId, ArrayList<SlickResult> results);
//
    public void addLogs(SlickLog slickLog);

//    public void addFiles(ArrayList<SlickFile> slickFiles);

}
