package com.krayem.hearthstone.ui.main.grid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import com.krayem.hearthstone.model.DefaultApiResponse
import com.krayem.hearthstone.model.ListApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class CardGridViewModel: ViewModel(){

    val cardSetResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()

    @Inject
    lateinit var databaseManager: DatabaseManager

//    private lateinit var subscription: Disposable

    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }

    fun getCardSet(cardSet:String){
        cardSetResponse.value = DefaultApiResponse.getLoading()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                cardSetResponse.postValue(ListApiResponse.getList(databaseManager.getCardSet(cardSet)))
            }catch (e:Exception){
                cardSetResponse.postValue(DefaultApiResponse.getError(e))
            }
        }
    }

    fun getFavourites(){
        cardSetResponse.value = DefaultApiResponse.getLoading()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                cardSetResponse.postValue(ListApiResponse.getList(databaseManager.getFavourites()))
            }catch (e:Exception){
                cardSetResponse.postValue(DefaultApiResponse.getError(e))
            }
        }
    }
}