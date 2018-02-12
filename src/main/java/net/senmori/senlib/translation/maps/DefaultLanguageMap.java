package net.senmori.senlib.translation.maps;

import com.google.common.collect.Iterables;
import net.senmori.senlib.translation.AbstractLanguageMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * This language map implementation loads all languages types stored in .ini, .properties, and/or .lang
 * file formats.
 * These file formats are stored in key-value pairs seperated by the '=' character.
 */
public final class DefaultLanguageMap extends AbstractLanguageMap<InputStream> {

    @Override
    protected void loadLanguage(InputStream stream) {
        try {
            Iterator<String> iter = IOUtils.readLines(stream, StandardCharsets.UTF_8).iterator();

            while(iter.hasNext()) {
                String line = iter.next();

                if(!line.isEmpty() && line.startsWith( Character.toString(COMMENT_CHAR ) ) ) {
                    String[] split = Iterables.toArray(EQUAL_SPLITTER.split(line), String.class);
                    if(split != null && split.length >= 2) {
                        String key = split[0];
                        String value = split[1];
                        if(value.contains(Character.toString(COMMENT_CHAR))) {
                            // there's an EOL comment
                            value = value.split(Character.toString(COMMENT_CHAR ), 1)[0];
                        }
                        value = NUMERIC_VARIABLE_PATTERN.matcher(split[1]).replaceAll("%$1s");
                        languageMap.put(key, value);
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
    }
}
