package com.slickqa.client.simple.definitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slickqa.client.simple.utils.JsonUtil;
import lombok.NonNull;

import java.nio.file.Path;

import static com.slickqa.client.simple.utils.JsonUtil.mapper;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickFile {
    private final Path filePath;
    private final SlickIdentity identity;
    private Integer chunkSize;
    private String mimeType;

    public SlickFile(@NonNull Path filePath, @NonNull SlickIdentity identity, Integer chunkSize, String mimeType) {
        this.filePath = filePath;
        this.identity = identity;
        this.chunkSize = chunkSize;
        this.mimeType = mimeType;
    }

    public void setChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public SlickIdentity getIdentity() {
        return identity;
    }

    public Integer getChunkSize() { return chunkSize; }

    public static SlickFileBuilder builder() {
        return new SlickFileBuilder();
    }

}
