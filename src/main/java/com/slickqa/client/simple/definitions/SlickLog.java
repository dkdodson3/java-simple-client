package com.slickqa.client.simple.definitions;

import lombok.NonNull;

import javax.ws.rs.client.Entity;
import java.util.List;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickLog {
    public final static String MESSAGE_LOG_EMPTY = "Logs are null or empty";

    private final String resultId;
    private final List<String> logs;

    public SlickLog(@NonNull String resultId, @NonNull List<String> logs) {
        this.resultId = resultId;
        this.logs = logs;

        if (logs.size() < 1) {
            throw new IllegalArgumentException(MESSAGE_LOG_EMPTY);
        }
    }

    public String getResultId() {
        return resultId;
    }

    public List<String> getLogs() {
        return logs;
    }

    public static SlickLogBuilder builder() {
        return new SlickLogBuilder();
    }

}
