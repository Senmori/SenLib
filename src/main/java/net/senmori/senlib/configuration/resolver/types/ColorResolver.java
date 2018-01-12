package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class ColorResolver extends ObjectResolver<Color> {
    public ColorResolver() {
        super(Color.class);
    }

    @Override
    public Color resolve(FileConfiguration config, String path) {
        if(!config.isColor(path)) {
            return null;
        }
        return config.getColor(path);
    }

    @Override
    public Color resolve(ConfigurationSection section, String path) {
        if(!section.isColor(path)) {
            return null;
        }
        return section.getColor(path);
    }
}
