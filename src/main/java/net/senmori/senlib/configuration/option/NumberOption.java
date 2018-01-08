package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class NumberOption extends ConfigOption<Number> {

    public static NumberOption newOption(String key, Number defaultValue) {
        return new NumberOption(key, defaultValue);
    }

    protected NumberOption(String key, Number defaultValue) {
        super(key, defaultValue, Number.class);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        String str = config.getString(getPath());
        if(str != null && !str.isEmpty() && NumberUtils.isParsable(str)) {
            // it's a number
            try {
                setValue(NumberUtils.createNumber(str));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void save(FileConfiguration config) {
        config.set(getPath(), getValue());
    }
}
