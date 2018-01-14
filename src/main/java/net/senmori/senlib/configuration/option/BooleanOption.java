package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import org.bukkit.configuration.file.FileConfiguration;

public class BooleanOption extends ConfigOption<Boolean> {

    public static BooleanOption newOption(String key, boolean defaultValue) {
        return new BooleanOption(key, defaultValue);
    }

    public BooleanOption(String key, Boolean defaultValue) {
        super(key, defaultValue, Boolean.class);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        String str = config.getString(getPath());
        if(str == null || str.isEmpty()) {
            return false;
        }
        setValue(Boolean.parseBoolean(str));
        return true;
    }

    @Override
    public boolean parse(String string) {
        setValue(Boolean.parseBoolean(string));
        return true;
    }

    @Override
    public boolean save(FileConfiguration config) {
        config.set(getPath(), getValue());
        return true;
    }
}
