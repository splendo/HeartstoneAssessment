package com.krayem.hearthstone.ui.main.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val isDbEmptyResponse: MutableLiveData<Boolean> = MutableLiveData()


    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }

    @Inject
    lateinit var databaseManager: DatabaseManager

    fun checkDatabase(){
        GlobalScope.launch {
            val isEmpty = databaseManager.isEmpty()
            isDbEmptyResponse.postValue(isEmpty)
            if(isEmpty){
                databaseManager.populateDb()
                isDbEmptyResponse.postValue(false)
            }
        }
    }


}
