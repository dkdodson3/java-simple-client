package com.slickqa.client.simple.definitions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import java.util.List;

/**
 * Created by Keith on 10/26/16.
 */
@Builder
@Getter
public class SlickLog {
    @NonNull private String resultId;
    @Singular private List<String> logs;
}
