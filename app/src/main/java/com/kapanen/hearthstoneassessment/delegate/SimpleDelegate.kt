package com.kapanen.hearthstoneassessment.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleDelegate<T, H : RecyclerView.ViewHolder>(
    protected val layoutId: Int
) : RecyclerViewAdapterDelegate<T, H> {

    override fun onViewRecycled(holder: H) {
        // do nothing
    }

    override val viewType: Int = layoutId

    override fun onCreateViewHolder(parent: ViewGroup): H {
        return onCreateViewHolder(
            parent,
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    protected abstract fun onCreateViewHolder(parent: ViewGroup, view: View): H

}
