package com.kapanen.hearthstoneassessment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.kapanen.hearthstoneassessment.data.CardType

@Parcelize
data class CardsTab(
    val cardType: CardType? = null,
    val isFavourites: Boolean = false
) : Parcelable
