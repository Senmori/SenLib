package net.senmori.senlib.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public abstract class JsonTypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public abstract JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext);

    @Override
    public abstract T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}
