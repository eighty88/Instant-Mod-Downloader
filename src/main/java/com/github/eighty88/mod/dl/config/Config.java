package com.github.eighty88.mod.dl.config;

public class Config {
    private String title;
    private String Json;

    public Config(String title, String Json) {
        this.title = title;
        this.Json = Json;
    }

    public String getTitle() {
        return title;
    }

    public String getJsonURL() {
        return Json;
    }
}
