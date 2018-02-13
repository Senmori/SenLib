package net.senmori.senlib.translation;

import java.util.Map;

public interface LanguageMap<K> {

    /**
     * Load a language from a given input.
     * @param input the type of input (e.g. FileConfiguration, InputStream, etc)
     */
    void loadLanguage(K input);

    /**
     * Translate the given string
     * @param key the key to translate
     * @param formatColor true to use {@link org.bukkit.ChatColor#translateAlternateColorCodes(char, String)}.
     * @return the translated string, or the key itself if not translatable
     */
    String translate(String key, boolean formatColor);

    /**
     * Translate the given key, then format it with the given arguments.
     * @param key the key to translate
     * @param formatColor true to use {@link org.bukkit.ChatColor#translateAlternateColorCodes(char, String)}.
     * @param args the additional arguments to format the string with (formatting is done via {@link java.text.MessageFormat})
     * @return the translated string, or the key itself if not translatable
     */
    String translate(String key, boolean formatColor, Object... args);

    /**
     * Check if the given key is loaded into the language map
     * @param key the key to check
     * @return true if the key is translatable
     */
    boolean isKeyTranslated(String key);

    /**
     * Attempt to convert the given object into the appropriate type to use in {@link #loadLanguage(Object)}
     * @param input the input to convert
     * @return the converted input, or null
     */
    K parseInput(Object input);

    Map<String, String> getLanguageMap();
}
