package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public class ChatColorOption extends ConfigOption<ChatColor> {

    public static ChatColorOption newOption(String key, ChatColor defaultValue) {
        return new ChatColorOption(key, defaultValue);
    }

    protected ChatColorOption(String key, ChatColor defaultValue) {
        super(key, defaultValue, ChatColor.class);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;
        // if it's not an '&' symbol
        String str = config.getString(getPath());
        if(str == null || str.isEmpty()) {
            return false;
        }
        try {
            ChatColor color = ChatColor.valueOf(config.getString(getPath()).toUpperCase());
            setValue(color);
            return getValue() == color;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void save(FileConfiguration config) {
        config.set(getPath(), getValue().name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public String toString() {
        return "ConfigOption={Path=" + getPath() + ", Value=" + getValue().name().toLowerCase() + "}";
    }
}
