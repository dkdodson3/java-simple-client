package com.slickqa.client.simple.definitions;

import lombok.NonNull;

public class SlickFileBuilder {
    private String resultId;
    private String filePath;
    private String name;

    public SlickFileBuilder addResultId(@NonNull String resultId) {
        this.resultId = resultId;
        return this;
    }

    public SlickFileBuilder addFilePath(@NonNull String filePath) {
        this.filePath = filePath;
        return this;
    }

    public SlickFileBuilder addName(String name) {
        this.name = name;
        return this;
    }

    public SlickFile build() {
        return new SlickFile(resultId, filePath, name);
    }
}