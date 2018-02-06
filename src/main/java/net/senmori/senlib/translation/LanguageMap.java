package net.senmori.senlib.translation;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public final class LanguageMap {
    private static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    private static final Splitter EQUAL_SPLITTER = Splitter.on("=").limit(2);

    private final ConcurrentHashMap<String, String> langList = new ConcurrentHashMap<>();
    private long lastUpdateTime;
    private char colorKey = '&';


    /**
     * Create a new Language map from the given InputStream.<br>
     * The InputStream must be from a .properties file.
     * @param stream the stream to inject
     */
    public LanguageMap(InputStream stream) {
        inject(stream);
    }

    /**
     * Set the color key to translate.
     * @param key the color key
     */
    public void setTranslationColorKey(char key) {
        this.colorKey = key;
    }

    /**
     * This is the {@link ChatColor} key that will be used to translate messages.
     * @return the color key
     */
    public char getColorKey() {
        return colorKey;
    }

    /**
     * Get an immutable copy of the language list containing all key value pairs.
     * @return an immutable copy of the language list
     */
    public Map<String, String> getLanguageList() {
        return ImmutableMap.copyOf(langList);
    }

    private void inject(InputStream stream) {
        Map<String, String> map = parseLangFile(stream);
        this.langList.putAll(map);
        this.lastUpdateTime = System.currentTimeMillis();
    }

    private Map<String,String> parseLangFile(InputStream stream) {
        Map<String, String> table = Maps.newHashMap();
        try {
            Iterator<String> iter = IOUtils.readLines(stream, StandardCharsets.UTF_8).iterator();

            while(iter.hasNext()) {
                String line = iter.next();

                if(!line.isEmpty() && line.charAt(0) != '#') {
                    String[] split = Iterables.toArray(EQUAL_SPLITTER.split(line), String.class);
                    if(split != null && split.length >= 2) {
                        String key = split[0];
                        String value = split[1];
                        if(value.indexOf('#') != -1) {
                            // there's an EOL comment
                            value = value.split("#", 1)[0];
                        }
                        value = NUMERIC_VARIABLE_PATTERN.matcher(split[1]).replaceAll("%$1s");
                        table.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return table;
    }

    /**
     * Replace the existing language list with the given list
     * @param newMap the new language list
     */
    public void replaceWith(Map<String, String> newMap) {
        this.langList.clear();
        this.langList.putAll(newMap);
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * Attempt to translate the key.<br>
     * If the key cannot be translated, this will return the given key.
     * @param key the key to translate
     * @param formatColor if this key should translate color codes
     * @return the translated, and color formatted, key
     */
    public String translate(String key, boolean formatColor) {
        return this.tryTranslateKey(key, formatColor);
    }

    /**
     * Attempts to translate, color format, and format the key with the given arguments.<br>
     * If the key could not be translated, this will return the given key.
     * @param key the key to translate
     * @param formatColor if the key should translate color codes
     * @param format the additional arguments to attempt to format that string with
     * @return the translated, and formatted, string
     */
    public String translateKeyFormatted(String key, boolean formatColor, Object... format) {
        String s = this.tryTranslateKey(key, formatColor);
        try {
            return String.format(s, format);
        } catch (IllegalArgumentException e) {
            return s;
        }
    }

    /**
     * Checks if the given key is present in the language map.
     * @param key the key to check
     * @return true if the key exists in the language map
     */
    public boolean isKeyTranslated(String key) {
        return this.langList.containsKey(key);
    }

    /**
     * Get the last time, in millis, the language map was updated.
     * @return the last time the language map was updated
     */
    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    private String tryTranslateKey(String key, boolean formatColor) {
        String s = this.langList.get(key);
        if(formatColor && s != null) {
            s = ChatColor.translateAlternateColorCodes(getColorKey(), s);
        }
        return s == null ? key : s;
    }

}
