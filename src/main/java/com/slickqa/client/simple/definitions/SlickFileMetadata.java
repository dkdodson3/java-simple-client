package com.slickqa.client.simple.definitions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Keith on 10/26/16.
 */
@Getter
@AllArgsConstructor
public class SlickFileMetadata {
    private String fileId;
    private String name;
    private String mimeType;
}
