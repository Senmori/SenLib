package net.senmori.senlib.configuration;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.senmori.senlib.configuration.option.SectionOption;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public abstract class ConfigManager implements IConfigurable {
    private static final String NULL_CONFIG_KEY = "<NULL>";

    // class variables
    private final BiMap<String, ConfigOption> options = HashBiMap.create();
    private final Set<IConfigurable> listeners = Sets.newHashSet();
    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final File configFile;

    public ConfigManager(JavaPlugin plugin, File configFile) {
        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.configFile = configFile;
        load(getConfig());
    }

    public <T extends ConfigOption> T registerOption(String key, T option) {
        options.put(key, option);
        return option;
    }

    public boolean registerListener(IConfigurable listener) {
        return listeners.add(listener);
    }

    @Nullable
    public ConfigOption<?> getOptionByPath(String path) {
        for(ConfigOption option : options.values()) {
            if(option instanceof SectionOption) {

                SectionOption section = (SectionOption)option;
                for(ConfigOption children : section.getOptions().values()) {

                    if(path.equals(section.getPath() + getConfig().options().pathSeparator() + children.getPath())) {
                        return children;
                    }
                }
            }
            if(option.getPath().equals(path)) {
                return option;
            }
        }
        return null;
    }

    public boolean load() {
        return load(getConfig());
    }

    public boolean load(final FileConfiguration config) {
        boolean values = getOptions().values().stream().allMatch(opt -> opt.save(config));
        boolean listeners = this.listeners.stream().allMatch(listener -> listener.load(config));
        return values && listeners;
    }

    public boolean save() {
        return save(getConfig());
    }

    public boolean save(final FileConfiguration config) {
        boolean values = getOptions().values().stream().allMatch(opt -> opt.save(config));
        boolean listeners = this.listeners.stream().allMatch(listener -> listener.save(config));
        return values && listeners;
    }

    private void saveFile() {
        try {
            getConfig().save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
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

    public Map<String, ConfigOption> getOptions() {
        return ImmutableMap.copyOf(options);
    }
}
