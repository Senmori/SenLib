package net.senmori.senlib.configuration.option;

import io.netty.util.internal.StringUtil;
import net.senmori.senlib.configuration.ConfigOption;
import org.bukkit.configuration.file.FileConfiguration;

public class StringOption extends ConfigOption<String> {

    public static StringOption newOption(String key, String defaultValue) {
        return new StringOption(key, defaultValue);
    }

    public StringOption(String key, String defaultValue) {
        super(key, defaultValue, String.class);
    }

    @Override
    public boolean parse(String string) {
        return !StringUtil.isNullOrEmpty((this.currentValue = string));
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        String str = config.getString(getPath());
        if(str != null && !str.isEmpty()) {
            setValue(str);
            return true;
        }
        return false;
    }

    @Override
    public boolean save(FileConfiguration config) {
        config.set(getPath(), getValue());
        return true;
    }
}
