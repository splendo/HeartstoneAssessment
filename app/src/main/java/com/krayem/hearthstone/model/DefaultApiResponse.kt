package com.krayem.hearthstone.model

open class DefaultApiResponse(val status: ReponseStatus) {

    lateinit var message: String

    companion object {
        fun getLoading() = DefaultApiResponse(ReponseStatus.LOADING)
        fun getError() = DefaultApiResponse(ReponseStatus.ERROR)
    }
}