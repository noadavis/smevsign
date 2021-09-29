package smevsign.api;

import com.google.gson.Gson;
import smevsign.support.ContainerConfig;
import smevsign.support.JsonConfig;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ConfigManager {
    public List<ContainerConfig> getJsonConfigContainers() {
        return jsonConfig.containers;
    }
    public String getDefaultAlias() {
        return jsonConfig.alias;
    }
    public boolean getDebug() {
        return jsonConfig.debug;
    }
    JsonConfig jsonConfig = null;
    public static ConfigManager instance;
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    public ConfigManager() {
        updateConfig();
    }
    public void updateConfig() {
        try {
            Gson gson = new Gson();
            String configPath = System.getProperty("user.home") + "/config.json";
            System.out.println(configPath);
            Reader reader = Files.newBufferedReader(Paths.get(configPath));
            this.jsonConfig = gson.fromJson(reader, JsonConfig.class);
            for (ContainerConfig container : jsonConfig.containers) {
                System.out.println(String.format("Loaded config for container with alias: %s", container.alias));
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
