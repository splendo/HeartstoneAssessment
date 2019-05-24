package me.grapescan.cards.ui

import android.app.Application
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository
import me.grapescan.cards.data.LocalCardRepository
import me.grapescan.cards.data.storage.InMemoryStorage
import me.grapescan.cards.data.storage.Storage
import me.grapescan.cards.ui.details.CardDetailsViewModel
import me.grapescan.cards.ui.list.CardListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(module {
                single<CardRepository> { LocalCardRepository(get()) }
                single<Storage<List<String>>> { InMemoryStorage(emptyList()) }
                viewModel { CardListViewModel(get()) }
                factory { (cardId: String) -> CardDetailsViewModel(cardId, get()) }
            })
        }
    }
}
