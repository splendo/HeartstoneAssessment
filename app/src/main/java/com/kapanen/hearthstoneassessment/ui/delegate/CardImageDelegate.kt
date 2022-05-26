package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.CardImageItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.CardImageItem

class CardImageDelegate :
    SimpleDelegate<CardImageItem, CardImageDelegate.ViewHolder>(R.layout.card_image_item) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: CardImageItem) {
        holder.binding.apply {
            cardImageItemTitle.text = data.title
            cardImageItemImage.setImageURI(data.img)
        }
    }

    override fun suitFor(position: Int, data: Any) = data is CardImageItem

    class ViewHolder(val binding: CardImageItemBinding) : RecyclerView.ViewHolder(binding.root)

}
