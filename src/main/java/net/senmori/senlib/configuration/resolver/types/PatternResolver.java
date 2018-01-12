package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class PatternResolver extends ObjectResolver<Pattern> {
    public PatternResolver() {
        super(Pattern.class);
    }

    @Override
    public Pattern resolve(FileConfiguration config, String path) {
        Object obj = config.get(path);
        return obj instanceof Pattern ? (Pattern)obj : null;
    }

    @Override
    public Pattern resolve(ConfigurationSection section, String path) {
        Object obj = section.get(path);
        return obj instanceof Pattern ? (Pattern)obj : null;
    }
}
