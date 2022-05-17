package com.krayem.hearthstone.network

import android.util.Log
import com.krayem.hearthstone.App
import com.krayem.hearthstone.R
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import com.krayem.hearthstone.di.DaggerViewModelInjector
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.objectbox.ObjectBox
import com.krayem.hearthstone.utils.JSON_TEST
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RequestInterceptor : Interceptor {


    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }


    @Inject
    lateinit var fakeServer: FakeServer


    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val cardSets = App.instance.resources.getStringArray(R.array.card_sets_array)

        var responseString = ""

        if (uri.endsWith("cards/all")) {
            responseString = fakeServer.getAll().toString()
        } else if (uri.endsWith("cards")) {

        }
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }

}