package com.slickqa.client.simple.definitions;

import lombok.NonNull;

public class SlickFileBuilder {
    private String resultId;
    private String filePath;
    private SlickIdentity identity;

    public SlickFileBuilder addResultId(String resultId) {
        this.resultId = resultId;
        return this;
    }

    public SlickFileBuilder addFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public SlickFileBuilder addIdentity(SlickIdentity identity) {
        this.identity = identity;
        return this;
    }

    public SlickFile build() {
        return new SlickFile(resultId, filePath, identity);
    }
}