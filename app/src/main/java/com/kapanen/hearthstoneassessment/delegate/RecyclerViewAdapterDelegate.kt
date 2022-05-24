package com.kapanen.hearthstoneassessment.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewAdapterDelegate<T, H : RecyclerView.ViewHolder> {

    fun onCreateViewHolder(parent: ViewGroup): H
    fun onBindViewHolder(holder: H, data: T)
    fun onViewRecycled(holder: H)
    fun suitFor(position: Int, data: Any): Boolean
    val viewType: Int

}
