package com.slickqa.client.simple.definitions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import javax.ws.rs.client.Entity;
import java.util.List;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickLog {
    private final String resultId;
    private final List<String> logs;

    public SlickLog(@NonNull String resultId, List<String> logs) {
        this.resultId = resultId;
        this.logs = logs;
    }

    public String getResultId() {
        return resultId;
    }

    public List<String> getLogs() {
        return logs;
    }

    public Entity toEntity(String mediaType) {
        return Entity.entity(this, mediaType);
    }

    public static SlickLogBuilder builder() {
        return new SlickLogBuilder();
    }

}
