package com.slickqa.client.simple;

import com.slickqa.client.simple.definitions.*;

/**
 * Created by Keith on 10/26/16.
 */
public interface SlickSimpleClient {

    public SlickTestRun addTestRun(SlickTestRun testRun);

    public void updateStatus(String resultId, SlickResultStatus status);

    public void addLog(SlickLog slickLog);

    public void addFile(SlickFile slickFile);

}
