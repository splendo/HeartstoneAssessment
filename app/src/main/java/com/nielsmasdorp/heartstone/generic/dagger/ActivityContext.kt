package com.nielsmasdorp.heartstone.generic.dagger

import android.content.Context
import javax.inject.Qualifier

/**
 * Annotation used to mark Activity [Context] instances
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext
