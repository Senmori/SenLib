package net.senmori.senlib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.senmori.senlib.configuration.ConfigOption;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static final String NULL_CONFIG_KEY = "<NULL>";

    // class variables
    private final BiMap<String, ConfigOption> options = HashBiMap.create();
    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final File configFile;

    public ConfigManager(JavaPlugin plugin, File configFile) {
        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.configFile = configFile;
        loadConfig();
    }


    public <T extends ConfigOption> T registerOption(String key, T option) {
        options.put(key, option);
        return option;
    }

    public ConfigOption<?> getOption(String key) {
        return options.get(key);
    }

    @Nullable
    public ConfigOption<?> getOptionByPath(String path) {
        return options.values().stream().filter(c -> c.getPath().equals(path)).limit(1).findFirst().orElse(null);
    }

    public String getConfigurationKey(ConfigOption option) {
        return options.inverse().getOrDefault(option, ConfigManager.NULL_CONFIG_KEY);
    }

    public void loadConfig() {
        options.values().forEach(configOption -> configOption.load(getConfig()));
    }

    public void saveConfig() {
        options.values().forEach( opt -> {
            opt.save(getConfig());
        });

        save();
    }

    private void save() {
        try {
            getConfig().save(getConfigFile());
        } catch (IOException e) {
        }
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getConfigFile() {
        return configFile;
    }
}
