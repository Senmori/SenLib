package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public abstract class ListResolver<V> extends ObjectResolver<List> {
    public ListResolver(Class<List> listClass) {
        super(List.class);
    }

    @Override
    public abstract List<V> resolve(FileConfiguration fileConfiguration, String s);

    @Override
    public abstract List<V> resolve(ConfigurationSection configurationSection, String s);
}
