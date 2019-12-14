package com.krayem.hearthstone.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class Card(
    val cardId: String? = null,
    val name: String? = null,
    val cardSet: String? = null,
    val type: String? = null,
    val rarity: String? = null,
    val cost: Int? = null,
    val attack: Int? = null,
    val health: Int? = null,
    val text: String? = null,
    val flavor: String? = null,
    val artist: String? = null,
    val collectible: Boolean? = false,
    val elite: Boolean? = false,
    val playerClass: String? = null,
    val multiClassGroup: String? = null,
    val classes: List<String>? = null,
    val howToGet: String? = null,
    val howToGetGold: String? = null,
    val img: String? = null,
    val imgGold: String? = null,
    val locale: String? = null,
    val mechanics: List<Mechanic>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("mechanics")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardId)
        parcel.writeString(name)
        parcel.writeString(cardSet)
        parcel.writeString(type)
        parcel.writeString(rarity)
        parcel.writeValue(cost)
        parcel.writeValue(attack)
        parcel.writeValue(health)
        parcel.writeString(text)
        parcel.writeString(flavor)
        parcel.writeString(artist)
        parcel.writeValue(collectible)
        parcel.writeValue(elite)
        parcel.writeString(playerClass)
        parcel.writeString(multiClassGroup)
        parcel.writeStringList(classes)
        parcel.writeString(howToGet)
        parcel.writeString(howToGetGold)
        parcel.writeString(img)
        parcel.writeString(imgGold)
        parcel.writeString(locale)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Card> {
        override fun createFromParcel(parcel: Parcel): Card {
            return Card(parcel)
        }

        override fun newArray(size: Int): Array<Card?> {
            return arrayOfNulls(size)
        }
    }
}