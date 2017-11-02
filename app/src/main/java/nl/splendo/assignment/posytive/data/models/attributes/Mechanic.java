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
 * Model class that represents a Mechanic in a Card Response from network and useful for persisting
 * the list of such attribute, Mechanic, in the DB with a many-to-many relationship
 */
@Table(database = LocalDatabase.class)
@ManyToMany(referencedTable = Card.class)
@Parcel(value = Parcel.Serialization.BEAN, analyze = {Mechanic.class})
public class Mechanic extends BaseModel {

    /** Id of the card (used as PrimaryKey in Card Table) */
    @PrimaryKey
    String cardId;

    /** Name of the Mechanic of the card */
    @SerializedName("name")
    @PrimaryKey
    String name;

    public String getCardId() {
        return cardId;
    }

    public String getMechanicName() {
        return getName();
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setMechanicName(String cardMechanicName) {
        setName(cardMechanicName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
