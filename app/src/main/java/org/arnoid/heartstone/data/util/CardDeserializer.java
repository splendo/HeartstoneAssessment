package org.arnoid.heartstone.data.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.CardMechanic;

import java.lang.reflect.Type;
import java.util.Set;

public class CardDeserializer implements JsonDeserializer<Card> {

    @Override
    public Card deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Card card = new Card();

        Type baseCardType = new TypeToken<BaseCard>() {
        }.getType();
        card.setBaseCard((BaseCard) context.deserialize(json, baseCardType));

        JsonObject jsonObject = json.getAsJsonObject();

        Type cardClassType = new TypeToken<Set<CardClass>>() {}.getType();
        JsonElement cardClassesJsonElement = jsonObject.get(Card.Scheme.Properties.CLASSES);
        card.setClasses((Set<CardClass>) context.deserialize(cardClassesJsonElement, cardClassType));

        Type mechanicsType = new TypeToken<Set<CardMechanic>>() {}.getType();
        JsonElement cardMechanicsJsonElement = jsonObject.get(Card.Scheme.Properties.MECHANICS);
        card.setMechanics((Set<CardMechanic>) context.deserialize(cardMechanicsJsonElement, mechanicsType));

        Type rarityType = new TypeToken<Set<CardRarity>>() {}.getType();
        JsonElement cardRarirityJsonElement = jsonObject.get(Card.Scheme.Properties.RARITY);
        card.setRarity((Set<CardRarity>) context.deserialize(cardRarirityJsonElement, rarityType));

        Type cardTypeType = new TypeToken<Set<CardType>>() {}.getType();
        JsonElement cardTypeJsonElement = jsonObject.get(Card.Scheme.Properties.TYPE);
        card.setType((Set<CardType>) context.deserialize(cardTypeJsonElement, cardTypeType));

        return card;
    }

}