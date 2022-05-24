package com.kapanen.hearthstoneassessment.delegate

import androidx.recyclerview.widget.RecyclerView

abstract class SimpleDelegate<T, H : RecyclerView.ViewHolder>(
    protected val layoutId: Int
) : RecyclerViewAdapterDelegate<T, H> {

    override fun onViewRecycled(holder: H) {
        // do nothing
    }

    override val viewType: Int = layoutId

}
