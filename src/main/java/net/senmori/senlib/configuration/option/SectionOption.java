package net.senmori.senlib.configuration.option;

import com.google.common.collect.Maps;
import net.senmori.senlib.LogHandler;
import net.senmori.senlib.configuration.ConfigOption;
import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SectionOption extends StringOption {
    protected SectionOption(String key) {
        super(key, key);
    }

    protected Map<String, ConfigOption> options = Maps.newHashMap();
    protected Map<Class, ObjectResolver> resolvers = Maps.newHashMap();
    protected ConfigurationSection section;

    public Map<String, ConfigOption> getOptions() {
        return options;
    }

    public ObjectResolver getResolver(Class clazz) {
        return resolvers.get(clazz);
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public <T extends ConfigOption> T addOption(String key, T option) {
        options.put(key, option);
        return option;
    }

    public ConfigOption getOptionByPath(String path) {
        for (ConfigOption option : getOptions().values()) {
            if (option.getPath().equals(path)) {
                return option;
            }
        }
        return null;
    }

    public <T extends ObjectResolver> T addResolver(T objectResolver) {
        resolvers.put(objectResolver.getValueClass(), objectResolver);
        return objectResolver;
    }

    @Override
    public void setValue(String key) {
        // NOOP on this type of option
    }

    @Override
    public boolean parse(String string) {
        return false; // NOOP
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;
        if(!config.isConfigurationSection(getPath())) return false;

        section = config.getConfigurationSection(getPath());

        AtomicBoolean error = new AtomicBoolean(false);
        for(String node : section.getKeys(false)) {
            getOptions().values().forEach(configOption -> {
                if(configOption.getPath().equals(node)) {

                    if(configOption.hasResolver()) {
                        configOption.setValue(configOption.getResolver().resolve(section, node));
                    } else {
                        Object obj = section.get(node);
                        if(resolvers.containsKey(obj.getClass())) {
                            configOption.setValue(resolvers.get(obj.getClass()).resolve(section, node));
                        } else {
                            if(!configOption.parse(section.getString(node)) ) {
                                error.set(true);
                                LogHandler.warning("Error loading config option " + configOption.toString());
                            }
                        }
                    }
                }
            });
        }
        return !error.get() && load(getSection());
    }

    @Override
    public boolean save(FileConfiguration config) {
        if(section == null || !config.isConfigurationSection(getPath())) {
            // create the section
            section = config.createSection(getPath());
        }
        return save(section);
    }

    public abstract boolean load(ConfigurationSection section);

    public abstract boolean save(ConfigurationSection section);

    public boolean hasResolver() {
        return getOptions().values().stream().anyMatch(ConfigOption::hasResolver) || !resolvers.isEmpty();
    }
}
