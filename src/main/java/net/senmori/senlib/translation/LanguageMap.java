package net.senmori.senlib.translation;

public interface LanguageMap {

    String translate(String key, boolean formatColor);

    String translate(String key, boolean formatColor, Object... args);

    boolean isKeyTranslated(String key);
}
