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
    public String getPath();

    /**
     * Gets the class that represents this option.
     * @return the class that represents this option.
     */
    public Class<T> getValueClass();

    /**
     * Gets the current value for this option.<br>
     * If the current value is null, the default value will be returned.
     * @return the current value for this option
     */
    public T getValue();

    /**
     * Set the value for this option.
     * @param value the new value
     */
    public void setValue(T value);
}
