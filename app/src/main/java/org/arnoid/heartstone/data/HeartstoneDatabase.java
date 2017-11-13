package org.arnoid.heartstone.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.arnoid.heartstone.data.dao.CardDao;
import org.arnoid.heartstone.data.relations.CardToCardClass;
import org.arnoid.heartstone.data.relations.CardToCardMechanic;
import org.arnoid.heartstone.data.relations.CardToCardRarity;
import org.arnoid.heartstone.data.relations.CardToCardType;

/**
 * Database definition.
 */
@Database(entities = {
        BaseCard.class,
        CardMechanic.class,
        CardClass.class,
        CardType.class,
        CardRarity.class,
        CardToCardClass.class,
        CardToCardMechanic.class,
        CardToCardRarity.class,
        CardToCardType.class,
},
        version = 1,
        exportSchema = false)
public abstract class HeartstoneDatabase extends RoomDatabase {

    public abstract CardDao cardDao();
}
