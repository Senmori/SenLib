package net.senmori.senlib.configuration.resolver;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public abstract class ObjectResolver<V> {

    private final Class<V> vClass;
    public ObjectResolver(Class<V> vClass) {
        this.vClass = vClass;
    }

    public Class<V> getValueClass() {
        return vClass;
    }

    public abstract V resolve(FileConfiguration config, String path);

    public abstract V resolve(ConfigurationSection section, String path);

    public void save(FileConfiguration config, String path, V object) {
        config.set(path, object);
    }

    public void save(ConfigurationSection section, String path, V object) {
        section.set(path, object);
    }

    public boolean equals(Object other) {
        return (other instanceof ObjectResolver) && ( (ObjectResolver) other ).getValueClass().equals(this.getValueClass());
    }
}
