package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;

import java.util.Optional;

public class PotionEffectResolver extends ObjectResolver<PotionEffect> {
    public PotionEffectResolver() {
        super(PotionEffect.class);
    }

    @Override
    public PotionEffect resolve(FileConfiguration config, String path) {
        Object obj = config.get(path);
        return obj instanceof PotionEffect ? (PotionEffect)obj : null;
    }

    @Override
    public PotionEffect resolve(ConfigurationSection section, String path) {
        Object obj = section.get(path);
        return obj instanceof PotionEffect ? (PotionEffect)obj :null;
    }
}
