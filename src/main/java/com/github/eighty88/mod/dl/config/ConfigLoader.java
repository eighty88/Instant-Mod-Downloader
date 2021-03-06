package com.github.eighty88.mod.dl.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConfigLoader {
    public Config getConfig() {
        Gson gson = new Gson();

        JsonReader reader = null;
        String path = "/config.json";
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            reader = new JsonReader(br);
            return gson.fromJson(Objects.requireNonNull(reader), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Config("Instant Mod Downloader", "");
    }
}
