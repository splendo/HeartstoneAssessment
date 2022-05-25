package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.CardIntStatItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.CardIntStatItem
import com.kapanen.hearthstoneassessment.model.CardStringStatItem

class CardIntStatItemDelegate :
    SimpleDelegate<CardIntStatItem, CardIntStatItemDelegate.ViewHolder>(R.layout.card_int_stat_item) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardIntStatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: CardIntStatItem) {
        holder.binding.apply {
            cardIntItemLabel.text = data.label
            cardIntItemValue.text = data.value.toString()
        }
    }

    override fun suitFor(position: Int, data: Any) = data is CardIntStatItem

    class ViewHolder(val binding: CardIntStatItemBinding) : RecyclerView.ViewHolder(binding.root)

}
