package com.slickqa.client.simple.definitions;

/**
 * Created by Keith on 10/25/16.
 */
public class SlickIdentity {
    private String name;
    private String id;

    public SlickIdentity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
