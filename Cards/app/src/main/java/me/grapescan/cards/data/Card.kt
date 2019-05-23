package me.grapescan.cards.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: String,
    val name: String,
    val imageUrl: String
) : Parcelable
