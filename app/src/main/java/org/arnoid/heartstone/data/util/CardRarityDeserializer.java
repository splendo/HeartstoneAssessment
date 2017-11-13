package org.arnoid.heartstone.data.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.arnoid.heartstone.data.CardRarity;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class CardRarityTypeConverter implements JsonDeserializer<Set<CardRarity>>{

    @Override
    public Set<CardRarity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        HashSet<CardRarity> cardRarities = new HashSet<>();
        cardRarities.add(new CardRarity(json.getAsString()));
        return cardRarities;
    }

}