package net.senmori.senlib.configuration.option;

import org.bukkit.World;

import java.util.List;

public class WorldListOption extends StringListOption {
    public WorldListOption(String key, List<String> defaultValue) {
        super(key, defaultValue);
    }

    public WorldListOption(String key, List<String> defaultValue, String description) {
        super(key, defaultValue, description);
    }

    public boolean excludeWorld(World world) {
        return excludeWorld(world, false);
    }

    public boolean excludeWorld(World world, boolean useUUIDs) {
        return list.add( useUUIDs ? world.getUID().toString() : world.getName() );
    }

    public boolean removeWorld(World world) {
        return list.removeIf(w -> w.equals(world.getName())) || list.removeIf(w -> w.equals(world.getUID().toString()));
    }

    public boolean contains(World world) {
        return list.stream().anyMatch(w -> w.equals(world.getName())) || list.stream().anyMatch(w -> w.equals(world.getUID().toString()));
    }
}
