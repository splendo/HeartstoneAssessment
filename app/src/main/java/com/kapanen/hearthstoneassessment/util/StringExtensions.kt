package com.kapanen.hearthstoneassessment.util

private const val ITEM_DELIMITER = "|"

/**
 * Using for updating base url to avoid issue
 * `Fatal Exception: java.lang.IllegalArgumentExceptionbaseUrl must end in /`
 */
fun String.withTrailingSlash() = if (this.endsWith("/")) this else "$this/"


fun List<String>.toItemsString(): String = this.joinToString(separator = ITEM_DELIMITER)

fun String.toStringList(): List<String> = this.split(ITEM_DELIMITER)

fun String.toStringSet(): Set<String> = this.split(ITEM_DELIMITER).toSet()

fun Set<String>.toItemsString(): String = this.joinToString(separator = ITEM_DELIMITER)
