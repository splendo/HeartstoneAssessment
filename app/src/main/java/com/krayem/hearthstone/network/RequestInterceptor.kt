package com.krayem.hearthstone.network

import com.krayem.hearthstone.App
import com.krayem.hearthstone.utils.JSON_TEST
import okhttp3.*
import org.json.JSONObject

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val responseString = when {
//            uri.endsWith("cards/all") -> JSON_TEST
            uri.endsWith("cards/all") -> {
                val jsonfile: String = App.instance.assets.open("cards.json").bufferedReader().use { it.readText() }
                val all = JSONObject(jsonfile)
                all.getJSONArray("Basic").toString()
            }
            else -> ""
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