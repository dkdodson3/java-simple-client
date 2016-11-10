package com.slickqa.client.simple;

import com.slickqa.client.simple.definitions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Keith on 10/26/16.
 */
public interface SlickSimpleClient {

    public SlickTestRun addTestRun(SlickTestRun testRun) throws IOException;

    public ArrayList<SlickResult> addResults(String testRunId, ArrayList<SlickResult> results) throws IOException;

    public void addLogs(String testRunId, String resultId, ArrayList<SlickLog> slickLogs);

    public ArrayList<SlickFile> addFiles(String testRunId, String resultId, ArrayList<SlickFile> slickFiles) throws IOException;

}
