package com.krayem.hearthstone.ui.main.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import com.krayem.hearthstone.model.DefaultApiResponse
import com.krayem.hearthstone.model.FavouriteCard_
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val favouriteResponse: MutableLiveData<Boolean> = MutableLiveData()
    val classesMechanicsResponse: MutableLiveData<Pair<Array<String>,Array<String>>> = MutableLiveData()
    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }

    @Inject
    lateinit var databaseManager:DatabaseManager


    fun checkIsFavourite(cardId:String){
        GlobalScope.launch (Dispatchers.Default){
            favouriteResponse.postValue(databaseManager.checkIsFavourite(cardId))
        }
    }

    fun toggleFavourite(cardId:String){
        GlobalScope.launch (Dispatchers.Default){
            favouriteResponse.postValue(databaseManager.toggleFavourite(cardId))
        }
    }

    fun getClassesAndMechanics(cardId:String){
        GlobalScope.launch (Dispatchers.Default){
            classesMechanicsResponse.postValue(Pair(databaseManager.getCardClasses(cardId),databaseManager.getCardMechanics(cardId)))
        }
    }
}
