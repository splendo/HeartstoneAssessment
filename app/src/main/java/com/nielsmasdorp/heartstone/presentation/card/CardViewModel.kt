package com.nielsmasdorp.heartstone.presentation.card

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * View model for a single card in Heartstone
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CardViewModel(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cardSet: String,
        val type: String,
        val rarity: String,
        val text: String) : Parcelable