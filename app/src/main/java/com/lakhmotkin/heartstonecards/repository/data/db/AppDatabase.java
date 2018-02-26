package com.lakhmotkin.heartstonecards.repository.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lakhmotkin.heartstonecards.repository.data.db.dao.CardConverters;
import com.lakhmotkin.heartstonecards.repository.data.db.dao.CardDao;
import com.lakhmotkin.heartstonecards.repository.model.Card;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

@Database(entities = {Card.class}, version = 4)
@TypeConverters({CardConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CardDao cardDao();

}
