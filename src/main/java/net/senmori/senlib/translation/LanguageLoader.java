package net.senmori.senlib.translation;

import com.google.common.collect.Maps;
import net.senmori.senlib.translation.maps.DefaultLanguageMap;
import net.senmori.senlib.translation.maps.JsonLanguageMap;
import net.senmori.senlib.translation.maps.YamlLanguageMap;

import java.util.Map;

public final class LanguageLoader {
    static {
        fileTypeAssociations = Maps.newHashMap();
        fileTypeMap = Maps.newHashMap();
        associate(FileType.JSON, JsonLanguageMap.class);
        associate(FileType.LANG, DefaultLanguageMap.class);
        associate(FileType.YAML, YamlLanguageMap.class);
        associate(FileType.INI, DefaultLanguageMap.class);
        associate(FileType.PROPERTIES, DefaultLanguageMap.class);
    }

    private static final Map<FileType, Class<? extends AbstractLanguageMap>> fileTypeAssociations;
    private static final Map<String, FileType> fileTypeMap;

    private LanguageLoader() { }

    public static FileType getLanguageFileType(String fileExtension) {
        return fileTypeMap.get(fileExtension.toLowerCase());
    }

    public static void associate(FileType type, Class<? extends AbstractLanguageMap> clazz) {
        fileTypeAssociations.putIfAbsent(type, clazz);
        fileTypeMap.putIfAbsent(type.getExtension(), type);
    }

    public static <T extends AbstractLanguageMap> T loadLanguage(FileType type) {
        Class<? extends AbstractLanguageMap> clazz = fileTypeAssociations.get(type);
        if (clazz != null) {
            try {
                return (T)clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
