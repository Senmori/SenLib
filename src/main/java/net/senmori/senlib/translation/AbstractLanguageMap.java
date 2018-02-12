package net.senmori.senlib.translation;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractLanguageMap<T> implements LanguageMap {
    protected static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    protected static final Splitter EQUAL_SPLITTER = Splitter.on("=").limit(2);
    protected static final char COMMENT_CHAR = '#';

    protected Map<String, String> languageMap = Maps.newHashMap();

    private char colorKey = '&';

    public char getColorKey() {
        return colorKey;
    }

    public void setColorKey(char colorKey) {
        this.colorKey = colorKey;
    }

    protected abstract void loadLanguage(T inputType);

    public String translate(String key, boolean formatColor) {
        return this.tryTranslateKey(key, formatColor);
    }

    public String translate(String key, boolean formatColor, Object... args) {
        String s = this.tryTranslateKey(key, formatColor);
        try {
            return MessageFormat.format(key, args);
        } catch (IllegalArgumentException e) {
            return s;
        }
    }

    public boolean isKeyTranslated(String key) {
        return languageMap.containsKey(key);
    }

    private String tryTranslateKey(String key, boolean formatColor) {
        String s = this.languageMap.get(key);
        if(formatColor && s != null) {
            s = ChatColor.translateAlternateColorCodes(getColorKey(), s);
        }
        return s == null ? key : s;
    }
}
