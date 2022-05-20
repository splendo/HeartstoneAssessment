package com.kapanen.heartstoneassessment.settings

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("NO_REFLECTION_IN_CLASS_PATH")
abstract class BasePropertyDelegate(private var keyPrefix: String = "") {

    abstract fun <T : Any> getValue(key: String, type: KClass<T>): T?

    abstract fun <T : Any> setValue(key: String, value: T?)

    fun key(property: KProperty<*>) = keyPrefix + property.name

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> getValue(thisRef: Any, property: KProperty<*>): T? {
        val typeClass = property.returnType.classifier as KClass<T>
        return getValue(key(property), typeClass)
    }

    operator fun <T : Any> setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        setValue(key(property), value)
    }

    fun <T : Any> default(value: T) = WithDefault(this, value)

    class WithDefault<T : Any> internal constructor(
        val base: BasePropertyDelegate,
        val default: T
    ) {

        inline operator fun <reified R : T> getValue(thisRef: Any, property: KProperty<*>): R {
            return base.getValue<R>(thisRef, property) ?: default as R
        }

        operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            base.setValue(thisRef, property, value)
        }

    }

}
