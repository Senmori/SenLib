package net.senmori.senlib.translation;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * This class holds the different types of files than can hold translation texts.
 */
public final class LanguageFileType {

    /** Represents a file type that holds key-value pairs in json format ("key":"value")*/
    public static final LanguageFileType JSON = new LanguageFileType("json");

    /** Represents a file type that holds key-value pairs in yaml format (key:value) */
    public static final LanguageFileType YAML = new LanguageFileType("yml");

    /** Represents a file type that holds key-value pairs in .lang format (key=value) */
    public static final LanguageFileType LANG = new LanguageFileType("lang");

    private static final Map<String, LanguageFileType> associations = Maps.newHashMap();

    private final String fileType;
    public LanguageFileType(String fileType) {
        this.fileType = fileType;
        associations.put(fileType, this);
    }

    public String getFileType() {
        return fileType;
    }

    /**
     * Get the matching {@link LanguageFileType} that has the matching file extension (without the leading period)
     * @param extension the extension (without a leading period) to look for
     * @return the matching {@link LanguageFileType} if found, otherwise null
     */
    public static LanguageFileType getTypeByExtension(String extension) {
        return associations.get(extension);
    }
}
