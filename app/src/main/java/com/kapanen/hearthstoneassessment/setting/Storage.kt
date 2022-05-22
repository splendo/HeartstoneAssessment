package com.kapanen.hearthstoneassessment.setting

interface Storage {

    operator fun <T> set(key: String, value: T?): Boolean

    operator fun <T> get(key: String, clazz: Class<T>, defaultValue: T?): T?

}
