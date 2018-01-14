package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import net.senmori.senlib.configuration.resolver.ObjectResolver;
import net.senmori.senlib.configuration.resolver.types.ChatColorResolver;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;
import java.util.Optional;

public class ChatColorOption extends ConfigOption<ChatColor> {
    private static final ChatColorResolver resolver = new ChatColorResolver();

    public ChatColorOption(String key, ChatColor defaultValue) {
        super(key, defaultValue, ChatColor.class);
        setResolver(resolver);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        setValue(resolver.resolve(config, getPath()));
        return this.currentValue != null;
    }

    @Override
    public boolean parse(String string) {
        try {
            ChatColor color = ChatColor.valueOf(string.toUpperCase());
            setValue(color);
        } catch(IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public void save(FileConfiguration config) {
        if(hasResolver()) {
            resolver.save(config, getPath(), getValue());
        } else {
            config.set(getPath(), getValue().name().toLowerCase());
        }

    }

    public boolean hasResolver() {
        return true;
    }

    public ChatColorResolver getResolver() {
        return resolver;
    }

    public void setResolver(ObjectResolver resolver) {
        // NOOP
    }

    @Override
    public String toString() {
        return "ConfigOption={Path=" + getPath() + ", Value=" + getValue().name().toLowerCase() + "}";
    }
}
