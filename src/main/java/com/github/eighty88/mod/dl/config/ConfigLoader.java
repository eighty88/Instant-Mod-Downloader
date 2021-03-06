package com.github.eighty88.mod.dl.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigLoader {
    public Config getConfig() {
        Gson gson = new Gson();

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(getClass().getResource("/config.json").getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Config config = gson.fromJson(reader, Config.class);
        return config;
    }
}
