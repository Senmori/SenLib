package net.senmori.senlib.translation;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractLanguageMap<T> implements LanguageMap<T> {
    protected static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    protected static final Splitter EQUAL_SPLITTER = Splitter.on("=").limit(2);
    protected static final char COMMENT_CHAR = '#';

    protected final Map<String, String> languageMap = Maps.newHashMap();

    private char colorKey = '&';

    public char getColorKey() {
        return colorKey;
    }

    public void setColorKey(char colorKey) {
        this.colorKey = colorKey;
    }

    @Override
    public String translate(String key, boolean formatColor) {
        return this.tryTranslateKey(key, formatColor);
    }

    @Override
    public String translate(String key, boolean formatColor, Object... args) {
        String s = this.tryTranslateKey(key, formatColor);
        if(s == null) {
            return key;
        }
        try {
            return MessageFormat.format(s, args);
        } catch (IllegalArgumentException e) {
            return s;
        }
    }

    @Override
    public boolean isKeyTranslated(String key) {
        return languageMap.containsKey( key );
    }

    @Override
    public Map<String, String> getLanguageMap() {
        return ImmutableMap.copyOf( languageMap );
    }

    private String tryTranslateKey(String key, boolean formatColor) {
        String s = this.languageMap.get(key);
        if(s != null && formatColor) {
            s = ChatColor.translateAlternateColorCodes(getColorKey(), s);
        }
        return s == null ? key : s;
    }
}
