package org.arnoid.heartstone.data.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.arnoid.heartstone.data.CardClass;

import java.lang.reflect.Type;

public class CardClassTypeConverter implements JsonDeserializer<CardClass> {

    @Override
    public CardClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new CardClass(json.getAsString());
    }
}