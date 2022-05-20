package com.kapanen.heartstoneassessment.settings

import kotlin.reflect.KClass

abstract class StoragePropertyDelegate(
    val storage: Storage,
    keyPrefix: String = ""
) : BasePropertyDelegate(keyPrefix) {

    override fun <T : Any> getValue(key: String, type: KClass<T>): T? {
        return storage[key, type.java, null]
    }

    override fun <T : Any> setValue(key: String, value: T?) {
        storage[key] = value
    }

}
