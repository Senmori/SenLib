package net.senmori.senlib.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * This interface represents an option in a configuration file
 * @param <T> what data type this option is
 */
public interface IConfigOption<T> {
    /**
     * The current path for this option.
     * @return the path of this option
     */
    String getPath();

    /**
     * Gets the class that represents this option.
     * @return the class that represents this option.
     */
    Class<T> getValueClass();

    /**
     * Gets the current value for this option.<br>
     * If the current value is null, the default value will be returned.
     * @return the current value for this option
     */
    T getValue();

    /**
     * Set the value for this option.
     * @param value the new value
     */
    void setValue(T value);
}
