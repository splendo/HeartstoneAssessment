package com.kapanen.hearthstoneassessment.util

import android.os.Build
import android.text.Html

/**
 * Using for updating base url to avoid issue
 * `Fatal Exception: java.lang.IllegalArgumentExceptionbaseUrl must end in /`
 */
fun String.withTrailingSlash() = if (this.endsWith("/")) this else "$this/"
