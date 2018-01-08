package net.senmori.senlib.configuration.option;

import net.senmori.senlib.configuration.ConfigOption;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Represents a config options that accepts a {@link Vector}.
 */
public class VectorOption extends ConfigOption<Vector> {

    public static VectorOption newOption(String key, int defaultRadius) {
        return new VectorOption(key, new Vector(defaultRadius, defaultRadius, defaultRadius), Vector.class);
    }

    public static VectorOption newOption(String key, Vector defaultVector) {
        return new VectorOption(key, defaultVector, Vector.class);
    }

    public VectorOption(String key, Vector defaultValue, Class<Vector> typeClass) {
        super(key, defaultValue, typeClass);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;

        Object obj = config.get(getPath());
        if(obj instanceof Vector) {
            setValue((Vector)obj);
        } else if(config.isList(getPath())){
            if(config.getIntegerList(getPath()) != null) {
                List<Integer> intList = config.getIntegerList(getPath());
                Vector result;
                switch (intList.size()) {
                    case 0:
                        result = this.defaultValue;
                        break;
                    case 1:
                        int x = intList.get(0);
                        result = new Vector(x, x, x);
                        break;
                    case 2:
                        result = new Vector(intList.get(0), intList.get(1), intList.get(0)); // use 'x' for x & z
                        break;
                    case 3:
                    default:
                        result = new Vector(intList.get(0), intList.get(1), intList.get(2));
                }
                setValue(result);
            }
        } else {
            int radius = config.getInt(getPath()); // default to a number
            setValue(new Vector(radius, radius, radius));
        }
        return true;
    }

    @Override
    public void save(FileConfiguration config) {
        config.set(getPath(), getValue());
    }
}
