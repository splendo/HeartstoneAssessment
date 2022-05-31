package com.kapanen.hearthstoneassessment.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kapanen.hearthstoneassessment.BuildConfig
import com.kapanen.hearthstoneassessment.api.CardsApiService
import com.kapanen.hearthstoneassessment.data.CardType
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.data.DefaultCardsRepository
import com.kapanen.hearthstoneassessment.data.local.DefaultLocalCardsDataSource
import com.kapanen.hearthstoneassessment.data.local.HearthstoneDatabase
import com.kapanen.hearthstoneassessment.data.local.LocalCardsDataSource
import com.kapanen.hearthstoneassessment.data.remote.DefaultRemoteCardsDataSource
import com.kapanen.hearthstoneassessment.data.remote.RemoteCardsDataSource
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.delegate.DefaultDelegatesManager
import com.kapanen.hearthstoneassessment.delegate.RecyclerViewAdapterDelegate
import com.kapanen.hearthstoneassessment.model.CardStringStatItem
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.setting.SharedPreferencesStorage
import com.kapanen.hearthstoneassessment.setting.Storage
import com.kapanen.hearthstoneassessment.ui.delegate.*
import com.kapanen.hearthstoneassessment.util.withTrailingSlash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

private const val APP_SETTINGS = "APP SETTINGS"
private const val OK_HTTP_CACHE_NAME = "okHttp-cache"
private const val DB_NAME = "hearthstone.db"
private const val CACHE_SIZE = 10 * 1024 * 1024L //10 MB

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseHearthstoneUrl

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class HomeCardTabs

    @BaseHearthstoneUrl
    @Provides
    fun provideBaseHearthstoneUrl() = BuildConfig.BASE_HEARTHSTONE_URL

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(
            APP_SETTINGS,
            Context.MODE_PRIVATE
        )

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideStorage(sharedPreferences: SharedPreferences, gson: Gson): Storage =
        SharedPreferencesStorage(sharedPreferences, gson)

    @Singleton
    @Provides
    fun provideAppSettings(storage: Storage) = AppSettings(storage)

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext context: Context) = context.resources

    @Provides
    fun provideHttpCache(@ApplicationContext context: Context): Cache? {
        return if (context.applicationContext.cacheDir != null) {
            val cacheDir = File(context.applicationContext.cacheDir, OK_HTTP_CACHE_NAME)
            Cache(cacheDir, CACHE_SIZE)
        } else {
            null
        }
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message: String? -> Timber.d(message) }
            .setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.HEADERS
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
    }

    @Provides
    fun provideDefaultOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache?
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    fun provideCardsApiService(
        okHttpClient: OkHttpClient,
        @BaseHearthstoneUrl baseUrl: String
    ): CardsApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl.withTrailingSlash())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CardsApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Singleton
    @RemoteDataSource
    @Provides
    fun provideCardsRemoteDataSource(
        cardsApiService: CardsApiService,
        ioDispatcher: CoroutineDispatcher
    ): RemoteCardsDataSource = DefaultRemoteCardsDataSource(cardsApiService, ioDispatcher)

    @Singleton
    @LocalDataSource
    @Provides
    fun provideCardsLocalDataSource(database: HearthstoneDatabase): LocalCardsDataSource =
        DefaultLocalCardsDataSource(database.cardsDao())

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): HearthstoneDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HearthstoneDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Singleton
    @HomeCardTabs
    @Provides
    fun provideCardsTabs() =
        listOf(CardsTab(isFavourites = true)) + CardType.values().map { CardsTab(it) }

}

@Module
@InstallIn(SingletonComponent::class)
object CardsRepositoryModule {

    @Singleton
    @Provides
    fun provideCardsRepository(
        @AppModule.RemoteDataSource remoteCardsDataSource: RemoteCardsDataSource,
        @AppModule.LocalDataSource localCardsDataSource: LocalCardsDataSource,
        appSettings: AppSettings,
        resources: Resources
    ): CardsRepository {
        return DefaultCardsRepository(
            remoteCardsDataSource,
            localCardsDataSource,
            appSettings,
            resources
        )
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DelegatesModule {
    @Suppress("UNCHECKED_CAST")
    @Singleton
    @Provides
    fun provideDelegateManager(
        cardsRepository: CardsRepository,
        ioDispatcher: CoroutineDispatcher,
        appSettings: AppSettings
    ): AdapterDelegatesManager {
        return DefaultDelegatesManager().apply {
            addDelegate(LoadingItemDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(NoDataItemDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(CardStringStatItemDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(CardIntStatItemDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(CardImageDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(
                FavouriteItemDelegate(
                    cardsRepository,
                    ioDispatcher
                ) as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>
            )
            addDelegate(
                CardDelegate(
                    cardsRepository,
                    ioDispatcher
                ) as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>
            )
            addDelegate(
                FilteringItemDelegate(
                    appSettings,
                    ioDispatcher
                ) as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>
            )
            addDelegate(FilteringHeaderDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
            addDelegate(UnknownItemDelegate() as RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>)
        }
    }
}
