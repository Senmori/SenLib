package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class FireworkEffectResolver extends ObjectResolver<FireworkEffect> {
    public FireworkEffectResolver() {
        super(FireworkEffect.class);
    }

    @Override
    public FireworkEffect resolve(FileConfiguration config, String path) {
        Object obj = config.get(path);
        return obj instanceof FireworkEffect ? (FireworkEffect)obj : null;
    }

    @Override
    public FireworkEffect resolve(ConfigurationSection section, String path) {
        Object obj = section.get(path);
        return obj instanceof FireworkEffect ? (FireworkEffect)obj : null;
    }
}
