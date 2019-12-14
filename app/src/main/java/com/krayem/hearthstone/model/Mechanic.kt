package com.krayem.hearthstone.model

import android.os.Parcel
import android.os.Parcelable


data class Mechanic(val name: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mechanic> {
        override fun createFromParcel(parcel: Parcel): Mechanic {
            return Mechanic(parcel)
        }

        override fun newArray(size: Int): Array<Mechanic?> {
            return arrayOfNulls(size)
        }
    }
}