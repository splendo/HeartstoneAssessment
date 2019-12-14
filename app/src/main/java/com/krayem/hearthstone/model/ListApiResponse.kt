package com.krayem.hearthstone.model


class ListApiResponse<T>(status: ReponseStatus,val items:List<T>): DefaultApiResponse(status) {

    companion object{
        fun <T> getList(items:List<T>) = ListApiResponse(ReponseStatus.SUCCESS,items)
    }
}