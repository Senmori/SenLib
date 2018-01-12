package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class AttributeModifierResolver extends ObjectResolver<AttributeModifier> {
    public AttributeModifierResolver() {
        super(AttributeModifier.class);
    }

    @Override
    public AttributeModifier resolve(FileConfiguration config, String path) {
        Object var = config.get(path);
        return (var instanceof AttributeModifier) ? (AttributeModifier)var : null;
    }

    @Override
    public AttributeModifier resolve(ConfigurationSection section, String path) {
        Object var = section.get(path);
        return (var instanceof AttributeModifier) ? (AttributeModifier)var : null;
    }
}
