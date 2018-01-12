package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class LocationResolver extends ObjectResolver<Location> {
    public LocationResolver() {
        super(Location.class);
    }

    @Override
    public Location resolve(FileConfiguration config, String path) {
        Object obj = config.get(path);
        return obj instanceof Location ? (Location)obj : null;
    }

    @Override
    public Location resolve(ConfigurationSection section, String path) {
        Object obj = section.get(path);
        return obj instanceof Location ? (Location)obj : null;
    }
}
