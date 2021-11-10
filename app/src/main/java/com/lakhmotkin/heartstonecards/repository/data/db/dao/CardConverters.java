package com.lakhmotkin.heartstonecards.repository.data.db.dao;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lakhmotkin.heartstonecards.repository.model.Mechanic;

import java.lang.reflect.Type;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

public class CardConverters {
    @TypeConverter
    public static ArrayList<Mechanic> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Mechanic>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Mechanic> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
