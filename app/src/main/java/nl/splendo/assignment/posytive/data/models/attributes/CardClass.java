package nl.splendo.assignment.posytive.data.models.attributes;

import com.google.gson.annotations.SerializedName;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.helpers.db.LocalDatabase;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Model class that represents a CardClass in a Card Response from network and useful for persisting
 * the list of such attribute, CardClass, in the DB with a Many-to-Many relationship
 */
@Table(database = LocalDatabase.class)
@ManyToMany(referencedTable = Card.class)
@Parcel(value = Parcel.Serialization.BEAN, analyze = {CardClass.class})
public class CardClass extends BaseModel {

    /** Id of the card (used as PrimaryKey in Card Table) */
    @PrimaryKey
    String cardId;

    /** Name of the CardClass of the card */
    @SerializedName("")
    @PrimaryKey
    String cardClassName;

    public String getCardId() {
        return cardId;
    }

    public String getCardClassName() {
        return cardClassName;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setCardClassName(String cardCardClassName) {
        cardClassName = cardCardClassName;
    }

}
