package net.senmori.senlib.configuration;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;

public abstract class ConfigOption<T> implements IConfigOption<T> {
    protected final String key;
    protected T defaultValue;
    protected T currentValue;
    protected Class<T> typeClass;
    private ObjectResolver resolver = null;
    private String description = "";

    protected ConfigOption(String key, T defaultValue, Class<T> typeClass) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.currentValue = this.defaultValue;
        this.typeClass = typeClass;
    }

    protected ConfigOption(String key, T defaultValue, Class<T> typeClass, String description) {
        this(key, defaultValue, typeClass);
        this.description = description;
    }

    @Override
    public String getPath() {
        return key;
    }

    @Override
    public Class<T> getValueClass() {
        return typeClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getValue() {
        return this.currentValue != null ? currentValue : defaultValue;
    }

    public void setValue(T value) {
        this.currentValue = value;
    }

    public boolean parse(String strValue) {
        try {
            this.currentValue = getValueClass().cast(strValue);
        } catch(ClassCastException e) {
            return false;
        }
        return true;
    }

    public boolean hasResolver() {
        return resolver != null;
    }

    @Nullable
    public ObjectResolver getResolver() {
        return resolver;
    }

    protected void setResolver(ObjectResolver resolver) {
        this.resolver = resolver;
    }

    public abstract boolean load(FileConfiguration config);

    public abstract boolean save(FileConfiguration config);

    public String toString() {
        return "ConfigOption={Path=" + getPath() + ", Value=" + getValue().toString() + ", Type=" + getValueClass().getName() + "}";
    }
}
