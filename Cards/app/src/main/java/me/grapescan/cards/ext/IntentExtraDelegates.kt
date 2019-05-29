package me.grapescan.cards.ext

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntExtra(private val defaultValue: Int = 0) : ReadWriteProperty<Intent, Int> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getIntExtra(property.name, defaultValue)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: Int) {
        thisRef.putExtra(property.name, value)
    }
}

class LongExtra(private val defaultValue: Long = 0) : ReadWriteProperty<Intent, Long> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getLongExtra(property.name, defaultValue)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: Long) {
        thisRef.putExtra(property.name, value)
    }
}

class BooleanExtra(private val defaultValue: Boolean = false) : ReadWriteProperty<Intent, Boolean> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getBooleanExtra(property.name, defaultValue)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: Boolean) {
        thisRef.putExtra(property.name, value)
    }
}

class StringExtra : ReadWriteProperty<Intent, String> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getStringExtra(property.name)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: String) {
        thisRef.putExtra(property.name, value)
    }
}

class ParcelableListExtra<T : Parcelable> : ReadWriteProperty<Intent, ArrayList<T>> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) =
        thisRef.getParcelableArrayListExtra<T>(property.name)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: ArrayList<T>) {
        thisRef.putExtra(property.name, value)
    }
}

@Suppress("UNCHECKED_CAST")
class SerializableExtra<T : Serializable> : ReadWriteProperty<Intent, T> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getSerializableExtra(property.name) as T

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: T) {
        thisRef.putExtra(property.name, value as Serializable)
    }
}

class ParcelableExtra<T : Parcelable> : ReadWriteProperty<Intent, T> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getParcelableExtra<T>(property.name)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: T) {
        thisRef.putExtra(property.name, value as Parcelable)
    }
}

class OptionalParcelableExtra<T : Parcelable?> : ReadWriteProperty<Intent, T> {

    override fun getValue(thisRef: Intent, property: KProperty<*>) = thisRef.getParcelableExtra<T>(property.name)

    override fun setValue(thisRef: Intent, property: KProperty<*>, value: T) {
        value?.let {
            thisRef.putExtra(property.name, it)
        }
    }
}
