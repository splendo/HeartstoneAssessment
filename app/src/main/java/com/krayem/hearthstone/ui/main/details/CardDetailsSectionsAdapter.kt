package com.krayem.hearthstone.ui.main.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krayem.hearthstone.R

class CardDetailsSectionsAdapter(
    private val context: Context,
    private val objects: ArrayList<Pair<String,String>>
) :
    RecyclerView.Adapter<CardDetailsSectionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailsSectionViewHolder {
        return CardDetailsSectionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_details_section,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return objects.size
    }

    override fun onBindViewHolder(holder: CardDetailsSectionViewHolder, position: Int) {
        val current = objects[position]
        holder.title.text = current.first
        holder.value.text = current.second
    }

}