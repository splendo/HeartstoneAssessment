package com.nielsmasdorp.heartstone.presentation

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.mockito.junit.MockitoJUnitRunner

/**
 * Custom test runner that remaps Rx schedulers to trampoline, for testing purposes.
 */
class RxMockitoJUnitRunner(classUnderTest: Class<*>) : MockitoJUnitRunner(classUnderTest) {

    init {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
    }
}