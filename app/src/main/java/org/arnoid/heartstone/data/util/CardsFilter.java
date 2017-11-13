package org.arnoid.heartstone.data.util;

import com.google.gson.Gson;

import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardMechanic;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent cards filtering data.
 */
public class CardsFilter {
    public enum Sorting {
        ASCENDING,
        DESCENDING
    }

    private boolean favouriteOnly;
    private List<CardRarity> rarity = new ArrayList<>();
    private List<CardClass> classes = new ArrayList<>();
    private List<CardMechanic> mechanics = new ArrayList<>();
    private List<CardType> types = new ArrayList<>();

    private Sorting alhabetic;

    public interface Filterable {
        boolean isChecked();

        void setChecked(boolean checked);

        String getName();
    }

    public CardsFilter favouritesOnly(boolean favouriteOnly) {
        this.favouriteOnly = favouriteOnly;
        return this;
    }

    public CardsFilter rarity(List<CardRarity> rarity) {
        this.rarity.addAll(rarity);
        return this;
    }

    public CardsFilter classes(List<CardClass> className) {
        this.classes.addAll(className);
        return this;
    }

    public CardsFilter mechanics(List<CardMechanic> mechanic) {
        this.mechanics.addAll(mechanic);
        return this;
    }

    public CardsFilter types(List<CardType> cardTypes) {
        this.types.addAll(cardTypes);
        return this;
    }

    public Sorting getAlhabetic() {
        return alhabetic;
    }

    public CardsFilter alhabetic(Sorting alhabetic) {
        this.alhabetic = alhabetic;
        return this;
    }

    public boolean isFavouriteOnly() {
        return favouriteOnly;
    }

    public void setFavouriteOnly(boolean favouriteOnly) {
        this.favouriteOnly = favouriteOnly;
    }

    public List<CardRarity> getRarity() {
        return rarity;
    }

    public List<CardClass> getClasses() {
        return classes;
    }

    public List<CardType> getTypes() {
        return types;
    }

    public List<CardMechanic> getMechanics() {
        return mechanics;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static CardsFilter fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CardsFilter.class);
    }

    @Override
    public String toString() {
        return "CardsFilter{" +
                "favouriteOnly=" + favouriteOnly +
                ", rarity=" + rarity +
                ", classes=" + classes +
                ", mechanics=" + mechanics +
                ", types=" + types +
                ", alhabetic=" + alhabetic +
                '}';
    }
}