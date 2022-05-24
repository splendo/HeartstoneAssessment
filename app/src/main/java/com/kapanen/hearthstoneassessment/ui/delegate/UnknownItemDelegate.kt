package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.UnknownItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate

class UnknownItemDelegate :
    SimpleDelegate<Any, UnknownItemDelegate.ViewHolder>(R.layout.unknown_item) {

    override fun suitFor(position: Int, data: Any) = true
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        UnknownItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: Any) = Unit

    class ViewHolder(binding: UnknownItemBinding) : RecyclerView.ViewHolder(binding.root)

}
