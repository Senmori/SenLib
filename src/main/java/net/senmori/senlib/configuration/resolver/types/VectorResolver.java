package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Optional;

public class VectorResolver extends ObjectResolver<Vector> {
    public VectorResolver() {
        super(Vector.class);
    }

    @Override
    public Vector resolve(FileConfiguration config, String path) {
        if(config.isVector(path)) {
            return config.getVector(path);
        }
        if (config.isList(path)){
            if(config.getIntegerList(path) != null) {
                List<Integer> intList = config.getIntegerList(path);
                Vector result = null;
                if(intList.isEmpty()) {
                    return null; // empty list
                }
                switch (intList.size()) {
                    case 0:
                        return null; // shouldn't reach here because we already check for an empty list
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
                return result;
            }
        } else {
            int radius = config.getInt(path); // default to a number
            return new Vector(radius, radius, radius);
        }
        return null;
    }

    @Override
    public Vector resolve(ConfigurationSection section, String path) {
        if(section.isVector(path)) {
            return section.getVector(path);
        }
        if (section.isList(path)){
            if(section.getIntegerList(path) != null) {
                List<Integer> intList = section.getIntegerList(path);
                Vector result = null;
                if(intList.isEmpty()) {
                    return null; // empty list
                }
                switch (intList.size()) {
                    case 0:
                        return null; // shouldn't reach here because we already check for an empty list
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
                return result;
            }
        } else {
            int radius = section.getInt(path); // default to a number
            return new Vector(radius, radius, radius);
        }
        return null;
    }
}
