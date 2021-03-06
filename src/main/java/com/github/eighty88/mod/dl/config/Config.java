package com.github.eighty88.mod.dl.config;

public class Config {
    private String title;
    private String updateJson;

    public Config(String title, String updateJson) {
        this.title = title;
        this.updateJson = updateJson;
    }

    public String getTitle() {
        return title;
    }

    public String getJsonURL() {
        return updateJson;
    }
}
