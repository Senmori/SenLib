package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class PlayerResolver extends ObjectResolver<OfflinePlayer> {
    public PlayerResolver() {
        super(OfflinePlayer.class);
    }

    @Override
    public OfflinePlayer resolve(FileConfiguration config, String path) {
        return config.isOfflinePlayer(path) ? config.getOfflinePlayer(path) : null;
    }

    @Override
    public OfflinePlayer resolve(ConfigurationSection section, String path) {
        return section.isOfflinePlayer(path) ? section.getOfflinePlayer(path) : null;
    }
}
