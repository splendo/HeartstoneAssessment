package com.kapanen.hearthstoneassessment.delegate

import androidx.recyclerview.widget.RecyclerView

class DefaultDelegatesManager : AdapterDelegatesManager {

    private val delegates: MutableMap<Int, RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>> =
        LinkedHashMap()

    override fun getDelegateFor(viewType: Int):
            RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder> {
        return delegates[viewType]
            ?: throw IllegalStateException("No delegate found for viewType $viewType")
    }

    override fun getViewTypeFor(position: Int, data: Any): Int {
        for (delegate in delegates.values) {
            if (delegate.suitFor(position, data)) {
                return delegate.viewType
            }
        }
        throw IllegalStateException("No delegate found for position $position and object $data")
    }

    override fun addDelegate(delegate: RecyclerViewAdapterDelegate<Any, RecyclerView.ViewHolder>):
            DefaultDelegatesManager {
        delegates[delegate.viewType] = delegate
        return this
    }

    fun getDelegates(): Collection<RecyclerViewAdapterDelegate<*, RecyclerView.ViewHolder>> {
        return delegates.values
    }

}
