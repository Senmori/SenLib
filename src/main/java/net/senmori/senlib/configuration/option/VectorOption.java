package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import net.senmori.senlib.configuration.resolver.ObjectResolver;
import net.senmori.senlib.configuration.resolver.types.VectorResolver;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Optional;

/**
 * Represents a config options that accepts a {@link Vector}.
 */
public class VectorOption extends ConfigOption<Vector> {
    private static final VectorResolver resolver = new VectorResolver();

    public VectorOption(String key, Vector defaultValue) {
        super(key, defaultValue, Vector.class);
        setResolver(resolver);
    }

    @Override
    public boolean parse(String string) {
        if(string != null && !string.isEmpty() && NumberUtils.isParsable(string)) {
            // it's a number
            try {
                int radius = NumberUtils.createInteger(string);
                setValue(new Vector(radius, radius, radius));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        setValue(resolver.resolve(config, getPath()));
        return this.currentValue != null;
    }

    @Override
    public boolean save(FileConfiguration config) {
        if(hasResolver()) {
            resolver.save(config, getPath(), getValue());
        } else {
            config.set(getPath(), getValue());
        }
        return true;
    }

    public boolean hasResolver() {
        return true;
    }

    public VectorResolver getResolver() {
        return this.resolver;
    }

    public void setResolver(ObjectResolver resolver) {
        // NOOP
    }
}
