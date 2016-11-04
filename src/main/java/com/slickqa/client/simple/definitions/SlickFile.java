package com.slickqa.client.simple.definitions;

import lombok.NonNull;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickFile {
    private final String resultId;
    private final String filePath;
    private final SlickIdentity identity;

    public SlickFile(@NonNull String resultId, @NonNull String filePath, @NonNull SlickIdentity identity) {
        this.resultId = resultId;
        this.filePath = filePath;
        this.identity = identity;
    }

    public String getResultId() {
        return resultId;
    }

    public String getFilePath() {
        return filePath;
    }

    public SlickIdentity getIdentity() {
        return identity;
    }

    public static SlickFileBuilder builder() {
        return new SlickFileBuilder();
    }

}
