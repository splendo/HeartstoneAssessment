package me.grapescan.cards.ui

import android.app.Application
import com.google.gson.Gson
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository
import me.grapescan.cards.data.LocalCardRepository
import me.grapescan.cards.data.storage.CardCatalogStorage
import me.grapescan.cards.data.storage.PersistentFavoritesStorage
import me.grapescan.cards.ui.details.CardDetailsViewModel
import me.grapescan.cards.ui.list.CardListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(module {
                single { Gson() }
                single<CardRepository> { LocalCardRepository(get(named("favorites")), get(named("catalog"))) }
                single(named("favorites")) { PersistentFavoritesStorage(get()) }
                single(named("catalog")) { CardCatalogStorage(get(), get()) }
                viewModel { CardListViewModel(get()) }
                factory { (initialCard: Card) -> CardDetailsViewModel(initialCard, get()) }
            })
        }
    }
}
