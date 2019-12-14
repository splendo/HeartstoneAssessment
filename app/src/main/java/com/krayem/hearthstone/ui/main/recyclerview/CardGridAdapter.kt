package com.krayem.hearthstone.ui.main.recyclerview


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.FavouriteCard
import com.krayem.hearthstone.model.FavouriteCard_
import com.krayem.hearthstone.objectbox.ObjectBox
import com.squareup.picasso.Picasso
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CardGridAdapter(
    private val context: Context,
    private val objects: ArrayList<Card>,
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
        holder.cardView.setOnClickListener {
            ViewCompat.setTransitionName(holder.image, "sharedImage" + current.cardId)
            adapterOnClick(current, holder.image)
        }
//        getTrackLayout(position)?.let { holder.raceImageIv.setImageResource(it) }
//        holder.raceTitleTv.text = current.raceName
//        holder.raceLocationTv.text = String.format(
//            context.getString(R.string.city_location_format),
//            current.cityName,
//            current.countryName
//        )
//        holder.circuitNameTv.text = current.circuitName
//        holder.raceFlagIv.setImageResource(R.drawable.ic_italy)
    }

//    private fun handleFlag(raceFlagIv:ImageView,current:Race){
//
//        when(current.flagUrl){
//            "ae" -> raceFlagIv.setImageResource(R.drawable.ae)
//            "at" -> raceFlagIv.setImageResource(R.drawable.at)
//            "au" -> raceFlagIv.setImageResource(R.drawable.au)
//            "az" -> raceFlagIv.setImageResource(R.drawable.az)
//            "be" -> raceFlagIv.setImageResource(R.drawable.be)
//            "bh" -> raceFlagIv.setImageResource(R.drawable.bh)
//            "br" -> raceFlagIv.setImageResource(R.drawable.br)
//            "ca" -> raceFlagIv.setImageResource(R.drawable.ca)
//            "cn" -> raceFlagIv.setImageResource(R.drawable.cn)
//            "de" -> raceFlagIv.setImageResource(R.drawable.de)
//            "es" -> raceFlagIv.setImageResource(R.drawable.es)
//            "fr" -> raceFlagIv.setImageResource(R.drawable.fr)
//            "gb" -> raceFlagIv.setImageResource(R.drawable.gb)
//            "hu" -> raceFlagIv.setImageResource(R.drawable.hu)
//            "it" -> raceFlagIv.setImageResource(R.drawable.it)
//            "jp" -> raceFlagIv.setImageResource(R.drawable.jp)
//            "mc" -> raceFlagIv.setImageResource(R.drawable.mc)
//            "mx" -> raceFlagIv.setImageResource(R.drawable.mx)
//            "ru" -> raceFlagIv.setImageResource(R.drawable.ru)
//            "sg" -> raceFlagIv.setImageResource(R.drawable.sg)
//            "us" -> raceFlagIv.setImageResource(R.drawable.us)
//        }
//    }

    fun replaceAll(objects: List<Card>) {
        this.objects.clear()
        this.objects.addAll(objects)
        notifyDataSetChanged()
    }
}