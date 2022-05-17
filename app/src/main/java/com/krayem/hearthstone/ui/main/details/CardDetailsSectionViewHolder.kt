package com.krayem.hearthstone.ui.main.details

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.card_details_section.view.*
import kotlinx.android.synthetic.main.card_grid_item.view.*

class CardDetailsSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val card: CardView = itemView.card_details_section_card
    val container: ConstraintLayout = itemView.card_details_section_container
    val title: TextView = itemView.card_details_section_title
    val value: TextView = itemView.card_details_section_value
}