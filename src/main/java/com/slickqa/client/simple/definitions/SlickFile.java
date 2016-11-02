package com.slickqa.client.simple.definitions;

import lombok.NonNull;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickFile {
    private final String resultId;
    private final String filePath;
    private final String name;

    public SlickFile(@NonNull String resultId, @NonNull String filePath, String name) {
        this.resultId = resultId;
        this.filePath = filePath;
        this.name = name;
    }

    public String getResultId() {
        return resultId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }

    public static SlickFileBuilder builder() {
        return new SlickFileBuilder();
    }

}
