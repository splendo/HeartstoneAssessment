package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.CardStringStatItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.CardStringStatItem

class CardStringStatItemDelegate :
    SimpleDelegate<CardStringStatItem, CardStringStatItemDelegate.ViewHolder>(R.layout.card_string_stat_item) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardStringStatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: CardStringStatItem) {
        holder.binding.apply {
            cardStringItemLabel.text = data.label
            cardStringItemValue.text = data.value
        }
    }

    override fun suitFor(position: Int, data: Any) = data is CardStringStatItem

    class ViewHolder(val binding: CardStringStatItemBinding) : RecyclerView.ViewHolder(binding.root)

}
