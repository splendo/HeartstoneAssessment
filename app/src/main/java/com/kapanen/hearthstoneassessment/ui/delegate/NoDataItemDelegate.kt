package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.NoDataItem

class NoDataItemDelegate :
    SimpleDelegate<NoDataItem, NoDataItemDelegate.ViewHolder>(R.layout.no_data_item) {

    override fun suitFor(position: Int, data: Any) = data is NoDataItem
    override fun onCreateViewHolder(parent: ViewGroup, view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, data: NoDataItem) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
