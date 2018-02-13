package net.senmori.senlib.translation;

/**
 * This class holds the different types of files than can hold translation texts.
 */
public final class FileType {

    /** Represents a file type that holds key-value pairs in json format ("key":"value")*/
    public static final FileType JSON = FileType.of("json");

    /** Represents a file type that holds key-value pairs in yaml format (key:value) */
    public static final FileType YAML = FileType.of("yml");

    /** Represents a file type that holds key-value pairs in .lang format (key=value) */
    public static final FileType LANG = FileType.of("lang");

    /** Represents a file type that holds key-value pairs in .ini format (key=value) */
    public static final FileType INI = FileType.of("ini");

    /** Represents a file type that holds key-value pairs in .properties format (key=value) */
    public static final FileType PROPERTIES = FileType.of("properties");

    private final String extension;
    private FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }


    public static FileType of(String type) {
        return new FileType(type);
    }
}
