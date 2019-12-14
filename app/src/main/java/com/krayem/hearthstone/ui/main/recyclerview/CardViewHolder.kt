package com.krayem.hearthstone.ui.main.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.card_grid_item.view.*

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardView: CardView = itemView.card_grid_item_cv
    val container: ConstraintLayout = itemView.card_grid_item_container
    val image: ImageView = itemView.card_grid_item_image
    val name: MaterialTextView = itemView.card_grid_item_name
    val favourite: MaterialCheckBox = itemView.card_grid_item_favourite
}