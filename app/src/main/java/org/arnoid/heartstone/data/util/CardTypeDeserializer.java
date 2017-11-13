package org.arnoid.heartstone.data.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.arnoid.heartstone.data.CardType;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class CardTypeDeserializer implements JsonDeserializer<Set<CardType>> {

    @Override
    public Set<CardType> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        HashSet<CardType> cardTypes = new HashSet<>();
        cardTypes.add(new CardType(json.getAsString()));
        return cardTypes;
    }

}