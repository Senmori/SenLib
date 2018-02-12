package net.senmori.senlib.translation.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import net.senmori.senlib.translation.AbstractLanguageMap;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * This language map implementation loads a language file stored in valid json format.
 */
public class JsonLanguageMap extends AbstractLanguageMap<InputStream> {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String JSON_COMMENT_KEY = "__";

    @Override
    protected void loadLanguage(InputStream input) {
        try {
            JsonObject object = gson.fromJson( new JsonReader( new InputStreamReader( input ) ), JsonObject.class );
            if(object.size() < 1) {
                throw new IllegalArgumentException( "Invalid JSON format. Language file cannot be empty." );
            }
            traverseObject( object );
        } catch (JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void traverseObject(JsonObject jsonObject) {
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {

            String key = entry.getKey();
            if( key.startsWith( Character.toString( COMMENT_CHAR ) ) || key.startsWith( JSON_COMMENT_KEY ) ) {
                continue;
            }

            JsonElement value = entry.getValue();
            if( !(value instanceof JsonPrimitive)) { // we don't want arrays yet
                continue;
            }

            String translationText = value.getAsString();
            languageMap.put( key, translationText );
        }
    }
}
