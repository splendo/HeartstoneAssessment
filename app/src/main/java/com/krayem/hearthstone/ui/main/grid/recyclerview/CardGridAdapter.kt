package com.krayem.hearthstone.ui.main.grid.recyclerview


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.FavouriteCard
import com.krayem.hearthstone.model.FavouriteCard_
import com.krayem.hearthstone.objectbox.ObjectBox
import com.squareup.picasso.Picasso
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class CardGridAdapter(
    private val context: Context,
    val objects: ArrayList<Card>,
    private val adapterOnClick: (Card, ImageView) -> Unit
) :
    RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_grid_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return objects.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val current: Card = objects[position]
        Picasso.get().load(current.img).placeholder(R.drawable.placeholder_card).error(R.drawable.placeholder_card).into(holder.image)
        holder.name.text = current.name
        if (current.cardId != null) {
            holder.favourite.setOnCheckedChangeListener { _, _ -> }
            val box: Box<FavouriteCard> = ObjectBox.boxStore.boxFor()
            holder.favourite.isChecked = box.query().equal(FavouriteCard_.cardId, current.cardId).build().count() > 0
            holder.favourite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    box.put(FavouriteCard(0, current.cardId))
                } else {
                    box.query().equal(FavouriteCard_.cardId, current.cardId).build().remove()
                }
            }
        }
        holder.clickableView.setOnClickListener {
            ViewCompat.setTransitionName(holder.image, "sharedImage" + current.cardId)
            adapterOnClick(current, holder.image)
        }

    }


    fun replaceAll(objects: List<Card>) {
        this.objects.clear()
        this.objects.addAll(objects)
        notifyDataSetChanged()
    }
}