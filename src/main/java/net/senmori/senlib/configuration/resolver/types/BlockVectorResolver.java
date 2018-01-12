package net.senmori.senlib.configuration.resolver.types;

import net.senmori.senlib.configuration.resolver.ObjectResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.Optional;

public class BlockVectorResolver extends ObjectResolver<BlockVector> {
    public BlockVectorResolver() {
        super(BlockVector.class);
    }

    @Override
    public BlockVector resolve(FileConfiguration config, String path) {
        if(!config.isVector(path)) {
            return null;
        }
        Vector vec = config.getVector(path);
        return vec instanceof BlockVector ? (BlockVector)vec : null;
    }

    @Override
    public BlockVector resolve(ConfigurationSection section, String path) {
        if(!section.isVector(path)) {
            return null;
        }
        Vector vec = section.getVector(path);
        return vec instanceof BlockVector ? (BlockVector)vec : null;
    }
}
