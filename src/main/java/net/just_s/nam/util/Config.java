package net.just_s.nam.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.just_s.nam.NotAliveMod;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static final File file = FabricLoader.getInstance().getConfigDir().resolve("NotAliveMod.json").toFile();
    private static final Gson gson = new Gson();
    public static List<String> ignitingWorlds = new ArrayList<>();

    public static void load() {
        if (file.exists()) {
            try {
                String json_string = Files.readString(Path.of(file.toString()), StandardCharsets.US_ASCII);
                for (JsonElement name : gson.fromJson(json_string, JsonObject.class).getAsJsonArray("worlds")) {
                    ignitingWorlds.add(name.getAsString());
                }
            } catch (Exception e) {
                NotAliveMod.LOGGER.warn("Could't load config: " + e);
            }
        }
        save();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder json_string = new StringBuilder("{\"worlds\":[");
            if (ignitingWorlds.size() > 0) {
                for (String name : ignitingWorlds) json_string.append('"').append(name).append("\", ");
                json_string.delete(json_string.lastIndexOf(", "), json_string.length());
            }
            json_string.append("]}");
            writer.write(json_string.toString());
        } catch (Exception e) {
            NotAliveMod.LOGGER.error("Could't save config: " + e);
        }
    }
}
