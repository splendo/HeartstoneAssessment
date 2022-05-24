package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate

class UnknownItemDelegate :
    SimpleDelegate<Any, UnknownItemDelegate.ViewHolder>(R.layout.unknown_item) {

    override fun suitFor(position: Int, data: Any) = true
    override fun onCreateViewHolder(parent: ViewGroup, view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, data: Any) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
