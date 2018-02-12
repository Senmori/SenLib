package net.senmori.senlib.translation.maps;

import net.senmori.senlib.translation.AbstractLanguageMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Set;

/**
 * This language map implementation loads a language file from a valid Bukkit {@link FileConfiguration}.
 */
public class YamlLanguageMap extends AbstractLanguageMap<FileConfiguration> {

    @Override
    protected void loadLanguage(FileConfiguration config) {
        Set<String> rootKeys = config.getKeys(false);

        for(String key : rootKeys) {
            if(config.isConfigurationSection( key )) {
                loadSection(config.getConfigurationSection( key ));
            } else if( canLoad(config, key )){
                languageMap.put(key, config.getString( key ));
            }
        }
    }

    private void loadSection(ConfigurationSection section) {
        char seperator = section.getRoot().options().pathSeparator();
        for(String node : section.getKeys( false )) {
            if(section.isConfigurationSection( node )) {
                loadSection(section.getConfigurationSection( node ));
            } else {
                languageMap.put(section.getCurrentPath() + seperator + node, section.getString( node ));
            }
        }
    }

    private boolean canLoad(FileConfiguration config, String key) {
        return !(config.isList(key) || config.isConfigurationSection( key ) || config.get( key ) instanceof ConfigurationSerializable);
    }
}
