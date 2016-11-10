package com.slickqa.client.simple.definitions;

import java.nio.file.Path;

public class SlickFileBuilder {
    private Path filePath;
    private SlickIdentity identity;
    private Integer chunkSize;
    private String mimeType;

    public SlickFileBuilder addFilePath(Path filePath) {
        this.filePath = filePath;
        return this;
    }

    public SlickFileBuilder addIdentity(SlickIdentity identity) {
        this.identity = identity;
        return this;
    }

    public SlickFileBuilder addChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public SlickFileBuilder addMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public SlickFile build() {
        return new SlickFile(filePath, identity, chunkSize, mimeType);
    }
}