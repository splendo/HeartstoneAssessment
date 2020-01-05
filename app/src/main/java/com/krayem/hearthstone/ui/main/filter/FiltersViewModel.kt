package com.krayem.hearthstone.ui.main.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import com.krayem.hearthstone.model.DefaultApiResponse
import com.krayem.hearthstone.model.ListApiResponse
import com.krayem.hearthstone.model.SectionFilter
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersViewModel : ViewModel() {
    val typesResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()
    val classesResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()
    val mechanicsResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()
    val filtersResponse: MutableLiveData<SectionFilter> = MutableLiveData()

    @Inject
    lateinit var databaseManager: DatabaseManager

    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }

    fun getTypes(){
        GlobalScope.launch(Dispatchers.Default) {
            typesResponse.postValue(ListApiResponse.getList(databaseManager.getTypes()))
        }
    }

    fun saveFilters(types: List<String>,
                    mechanics: List<String>,
                    classes: List<String>,
                    minRarity: Int,
                    maxRarity: Int,
                    sortBy: Int,
                    descending: Boolean){
        GlobalScope.launch(Dispatchers.Default) {
            databaseManager.saveFilters(types, mechanics, classes, minRarity, maxRarity, sortBy, descending)
        }
    }

    fun getClasses(){
        GlobalScope.launch(Dispatchers.Default) {
            classesResponse.postValue(ListApiResponse.getList(databaseManager.getClasses()))
        }
    }

    fun getMechanics(){
        GlobalScope.launch(Dispatchers.Default) {
            mechanicsResponse.postValue(ListApiResponse.getList(databaseManager.getMechanics()))
        }
    }

    fun clearFilters(){
        GlobalScope.launch(Dispatchers.Default){
            databaseManager.clearFilters()
        }
    }

    fun getFilters(){
        GlobalScope.launch (Dispatchers.Default){
            filtersResponse.postValue(databaseManager.getFilters())
        }
    }
}
