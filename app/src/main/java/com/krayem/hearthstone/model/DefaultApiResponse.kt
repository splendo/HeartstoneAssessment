package com.krayem.hearthstone.model

open class DefaultApiResponse(val status: ResponseStatus, val errorMessage : String? = null) {

    companion object {
        fun getLoading() = DefaultApiResponse(ResponseStatus.LOADING)
        fun getError(it:Throwable) = DefaultApiResponse(ResponseStatus.ERROR,it.message)
    }
}