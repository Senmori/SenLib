package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemStackResolver extends ObjectResolver<ItemStack> {
    public ItemStackResolver() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack resolve(FileConfiguration config, String path) {
        return config.getItemStack(path);
    }

    @Override
    public ItemStack resolve(ConfigurationSection section, String path) {
        return section.getItemStack(path);
    }
}
