package com.krayem.hearthstone.model


class ListApiResponse<T>(status: ResponseStatus, val items:List<T>): DefaultApiResponse(status) {

    companion object{
        fun <T> getList(items:List<T>) = ListApiResponse(ResponseStatus.SUCCESS,items)
    }
}