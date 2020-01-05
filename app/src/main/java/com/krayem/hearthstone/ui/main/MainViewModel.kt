package com.krayem.hearthstone.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krayem.hearthstone.di.DaggerViewModelInjector
import com.krayem.hearthstone.model.*
import com.krayem.hearthstone.network.CardApi
import com.krayem.hearthstone.network.NetworkModule
import com.krayem.hearthstone.objectbox.ObjectBox
import com.krayem.hearthstone.utils.fromJsonArray
import com.krayem.hearthstone.utils.getRarityPair
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.objectbox.Box
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.StringReader
import javax.inject.Inject


class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val allResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()
    val cardSetResponse: MutableLiveData<DefaultApiResponse> = MutableLiveData()

    @Inject
    lateinit var cardApi: CardApi

    private lateinit var subscription: Disposable

    init {
        DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()
            .inject(this)
    }

//    fun getCardSet(cardSet: String) {
//        subscription = cardApi.getCardSet(cardSet)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                cardSetResponse.postValue(DefaultApiResponse.getLoading())
//            }
//            .subscribe(
//                { cardList ->
//                    cardSetResponse.postValue(ListApiResponse.getList(cardList))
//                },
//                {
//                    it.printStackTrace()
//                    cardSetResponse.postValue(DefaultApiResponse.getError(it))
//                }
//            )
//    }

//    fun getAll() {
//        subscription = cardApi.getAll()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                allResponse.postValue(DefaultApiResponse.getLoading())
//            }
//            .subscribe(
//                { cardList ->
//                    val typesBox: Box<SectionFilter> = ObjectBox.boxStore.boxFor()
//                    val filter = typesBox.query().equal(SectionFilter_.index, 1).build().findFirst()
//                    var selectedTypes: Set<String>? = null
//                    var selectedClasses: Set<String>? = null
//                    var selectedMechanics: Set<String>? = null
//                    val anotherMutableList = mutableListOf<Card>()
//
//                    if (filter != null) {
//                        val filtersJSONObject = JSONObject(filter.typesJsonArray)
//
//                        val typesFilter = filtersJSONObject.getJSONArray("types")
//                        if (typesFilter != null) {
//                            selectedTypes = fromJsonArray(typesFilter)
//                        }
//
//                        val classesFilter = filtersJSONObject.getJSONArray("classes")
//                        if (classesFilter != null) {
//                            selectedClasses = fromJsonArray(classesFilter)
//                        }
//
//                        val mechanicsFilter = filtersJSONObject.getJSONArray("mechanics")
//                        if (mechanicsFilter != null) {
//                            selectedMechanics = fromJsonArray(mechanicsFilter)
//                        }
//
//                        if (selectedTypes != null && selectedTypes.isNotEmpty()) {
//                            cardList.forEach {
//                                if (selectedTypes.contains(it.type)) {
//                                    anotherMutableList.add(it)
//                                }
//                            }
//                        } else {
//                            anotherMutableList.addAll(cardList)
//                        }
//
//                        val rarityPair = getRarityPair(filtersJSONObject)
//                    }
//                    allResponse.postValue(
//                        ListApiResponse.getList(
//                            anotherMutableList.sortedWith(
//                                CardComparator()
//                            )
//                        )
//                    )
//                },
//                {
//                    allResponse.postValue(DefaultApiResponse.getError(it))
//                }
//            )
//    }

//    override fun onCleared() {
//        super.onCleared()
//        subscription.dispose()
//    }


}
