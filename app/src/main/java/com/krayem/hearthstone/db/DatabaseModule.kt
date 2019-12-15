package com.krayem.hearthstone.db


import com.krayem.hearthstone.network.FakeServer
import com.krayem.hearthstone.utils.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideDatabaseManager(): DatabaseManager {
        return DefaultDatabaseManager()
    }

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideFakeServer(): FakeServer {
        return FakeServer()
    }

}