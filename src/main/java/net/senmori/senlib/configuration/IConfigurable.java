package net.senmori.senlib.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * This interface is used as a contract for all classes that want to load/save from a
 * {@link ConfigManager}.
 */
public interface IConfigurable {

    public boolean load(FileConfiguration config);

    public boolean save(FileConfiguration config);
}
