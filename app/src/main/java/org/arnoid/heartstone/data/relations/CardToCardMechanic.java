package org.arnoid.heartstone.data.relations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.CardClass;

@Entity(indices = {
        @Index(value = {
                BaseCard.Scheme.Properties.CARD_ID,
                CardClass.Scheme.Properties.ID
        }, unique = true),
})

public class CardToCardClass {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = BaseCard.Scheme.Properties.CARD_ID)
    private String cardId;
    @ColumnInfo(name = CardClass.Scheme.Properties.ID)
    private int cardClassId;
}
