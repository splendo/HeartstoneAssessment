package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.Card

class CardDelegate : SimpleDelegate<Card, CardDelegate.ViewHolder>(R.layout.card_item) {

    override fun suitFor(position: Int, data: Any) = data is Card
    override fun onCreateViewHolder(parent: ViewGroup, view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, data: Card) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
