package com.slickqa.client.simple.definitions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Keith on 10/26/16.
 */
@AllArgsConstructor
@Getter
public class SlickFile {
    @NonNull private String resultId;
    @NonNull private String filePath;
    @Setter private String name;
}
