package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Optional;

public class SerializableObjectResolver extends ObjectResolver<ConfigurationSerializable> {
    public SerializableObjectResolver() {
        super(ConfigurationSerializable.class);
    }

    @Override
    public ConfigurationSerializable resolve(FileConfiguration config, String path) {
        Object obj =  config.get(path);
        return obj instanceof ConfigurationSerializable ? (ConfigurationSerializable) obj : null;
    }

    @Override
    public ConfigurationSerializable resolve(ConfigurationSection section, String path) {
        Object obj =  section.get(path);
        return obj instanceof ConfigurationSerializable ? (ConfigurationSerializable) obj : null;
    }
}
