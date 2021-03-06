package com.github.eighty88.mod.dl.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

public class ConfigLoader {
    public Config getConfig() {
        Gson gson = new Gson();

        JsonReader reader = null;
        try {
            URI uri = getClass().getResource("/config.json").toURI();
            final String[] array = uri.toString().split("!");
            final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), new HashMap<>());
            final Path path = fs.getPath(array[1]);
            reader = new JsonReader(new FileReader(path.toFile()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(Objects.requireNonNull(reader), Config.class);
    }
}
