package net.senmori.senlib.translation;

import com.google.common.collect.Maps;
import net.senmori.senlib.translation.maps.DefaultLanguageMap;
import net.senmori.senlib.translation.maps.JsonLanguageMap;
import net.senmori.senlib.translation.maps.YamlLanguageMap;

import java.util.Map;

public final class LanguageLoader {
    private static final LanguageLoader INSTANCE = new LanguageLoader();

    public static LanguageLoader getInstance() {
        return INSTANCE;
    }

    private final Map<LanguageFileType, Class<? extends AbstractLanguageMap>> fileTypeAssociations = Maps.newHashMap();

    private LanguageLoader() {
        associate(LanguageFileType.JSON, JsonLanguageMap.class);
        associate(LanguageFileType.LANG, DefaultLanguageMap.class);
        associate(LanguageFileType.YAML, YamlLanguageMap.class);
    }

    public void associate(LanguageFileType type, Class<? extends AbstractLanguageMap> clazz) {
        fileTypeAssociations.putIfAbsent(type, clazz);
    }

    public <T extends AbstractLanguageMap> T loadLanguage(LanguageFileType type) {
        Class<? extends AbstractLanguageMap> clazz = fileTypeAssociations.get(type);
        if(clazz != null) {
            try {
                AbstractLanguageMap instance = clazz.newInstance();
                return (T) instance;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
