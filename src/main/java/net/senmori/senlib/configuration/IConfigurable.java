package net.senmori.senlib.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * This interface is used as a contract for all classes that want to load/save from a
 * {@link ConfigManager}.
 */
public interface IConfigurable {

    boolean load(FileConfiguration config);

    boolean save(FileConfiguration config);
}
