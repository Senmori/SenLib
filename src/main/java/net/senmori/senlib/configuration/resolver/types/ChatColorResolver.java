package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Optional;

public class ChatColorResolver extends ObjectResolver<ChatColor> {
    public ChatColorResolver() {
        super(ChatColor.class);
    }

    @Override
    public ChatColor resolve(FileConfiguration config, String path) {
        try {
            return ChatColor.valueOf(config.getString(path).toUpperCase());
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public ChatColor  resolve(ConfigurationSection section, String path) {
        try {
            return ChatColor.valueOf(section.getString(path).toUpperCase());
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void save(FileConfiguration config, String path, ChatColor object) {
        config.set(path, object.name().toLowerCase());
    }

    @Override
    public void save(ConfigurationSection section, String path, ChatColor color) {
        section.set(path, color.name().toLowerCase());
    }
}
