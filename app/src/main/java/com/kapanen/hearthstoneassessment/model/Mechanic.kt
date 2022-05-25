package com.kapanen.hearthstoneassessment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @param name
 */
@Parcelize
data class Mechanic(
    val name: String
) : Parcelable
