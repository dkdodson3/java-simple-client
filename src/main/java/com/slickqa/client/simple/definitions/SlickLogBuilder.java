package com.slickqa.client.simple.definitions;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SlickLogBuilder {
    private String resultId;
    private List<String> logs;

    public SlickLogBuilder addResultId(String resultId) {
        this.resultId = resultId;
        return this;
    }

    public SlickLogBuilder addLog(String log) {
        if (this.logs == null) {
            this.logs = new ArrayList<>();
        }

        this.logs.add(log);
        return this;
    }

    public SlickLogBuilder addLogs(List<String> logs) {
        if (this.logs == null) {
            this.logs = new ArrayList<>();
        }

        this.logs.addAll(logs);
        return this;
    }

    public SlickLog build() {
        return new SlickLog(resultId, logs);
    }
}