package com.krayem.hearthstone.db


import com.krayem.hearthstone.network.FakeServer
import dagger.Module
import dagger.Provides
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