package com.kapanen.hearthstoneassessment.delegate

import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegatesManager {

    fun getDelegateFor(viewType: Int): RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>

    fun getViewTypeFor(position: Int, data: Any): Int

    fun addDelegate(delegate: RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>): DefaultDelegatesManager

}
