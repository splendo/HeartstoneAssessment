package com.krayem.hearthstone.ui.main

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat

import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.FavouriteCard
import com.krayem.hearthstone.model.FavouriteCard_
import com.krayem.hearthstone.objectbox.ObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.card_details_fragment.*
import kotlinx.android.synthetic.main.card_details_pager_fragment.*

class CardDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CardDetailsFragment()
    }

    private lateinit var viewModel: CardDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card: Card? = arguments?.getParcelable("CARD_TEST")
        htab_header.setImageBitmap(arguments?.getParcelable("BITMAP_TEST"))
        ViewCompat.setTransitionName(htab_header, "sharedImage" + card?.cardId)
        htab_toolbar.title = card?.name
        htab_toolbar.setNavigationIcon(R.drawable.ic_back)
        htab_toolbar.setNavigationOnClickListener { activity?.supportFragmentManager?.popBackStack() }


        if(card?.name != null){
            card_details_name_label.text = String.format(getString(R.string.card_details_name_label),card.name)
            card_details_name_label.visibility = View.VISIBLE

        }else {
            card_details_name_label.visibility = View.GONE
        }

        if(card?.cardSet != null){
            card_details_set_label.text = String.format(getString(R.string.card_details_set_label),card.cardSet)
            card_details_set_label.visibility = View.VISIBLE

        }else {
            card_details_set_label.visibility = View.GONE
        }

        if(card?.type != null){
            card_details_type_label.text = String.format(getString(R.string.card_details_type_label),card.type)
            card_details_type_label.visibility = View.VISIBLE

        }else {
            card_details_type_label.visibility = View.GONE
        }





        if(card?.rarity != null){
            card_details_rarity_label.text = String.format(getString(R.string.card_details_rarity_label),card.rarity)
            card_details_rarity_label.visibility = View.VISIBLE

        }else {
            card_details_rarity_label.visibility = View.GONE
        }

        if(card?.cost != null){
            card_details_cost_label.text = String.format(getString(R.string.card_details_cost_label),card.cost)
            card_details_cost_label.visibility = View.VISIBLE

        }else {
            card_details_cost_label.visibility = View.GONE
        }

        if(card?.attack != null){
            card_details_attack_label.text = String.format(getString(R.string.card_details_attack_label),card.attack)
            card_details_attack_label.visibility = View.VISIBLE

        }else {
            card_details_attack_label.visibility = View.GONE
        }

        if(card?.health != null){
            card_details_health_label.text = String.format(getString(R.string.card_details_health_label),card.health)
            card_details_health_label.visibility = View.VISIBLE

        }else {
            card_details_health_label.visibility = View.GONE
        }

        if(card?.text != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                card_details_text_label.text = Html.fromHtml(card.text,Html.FROM_HTML_MODE_COMPACT)
            }
            card_details_text_label.visibility = View.VISIBLE

        }else {
            card_details_text_label.visibility = View.GONE
        }

        if(card?.flavor != null){
            card_details_flavor_label.text = String.format(getString(R.string.card_details_flavor_label),card.flavor)
            card_details_flavor_label.visibility = View.VISIBLE

        }else {
            card_details_flavor_label.visibility = View.GONE
        }

        if(card?.artist != null){
            card_details_artist_label.text = String.format(getString(R.string.card_details_artist_label),card.artist)
            card_details_artist_label.visibility = View.VISIBLE

        }else {
            card_details_artist_label.visibility = View.GONE
        }

        if(card?.playerClass != null){
            card_details_player_class_label.text = String.format(getString(R.string.card_details_player_class_label),card.playerClass)
            card_details_player_class_label.visibility = View.VISIBLE

        }else {
            card_details_player_class_label.visibility = View.GONE
        }
        if(card?.multiClassGroup != null){
            card_details_multi_class_group_label.text = String.format(getString(R.string.card_details_multi_class_group_label),card.multiClassGroup)
            card_details_multi_class_group_label.visibility = View.VISIBLE

        }else {
            card_details_multi_class_group_label.visibility = View.GONE
        }
        if(card?.classes != null){
            card_details_classes_label.visibility = View.VISIBLE
            card_details_classes_label.text = getString(R.string.card_details_classes_label)
            card.classes.forEach {
                card_details_classes_label.text = card_details_classes_label.text.toString() + it + ","
            }
        }else {
            card_details_classes_label.visibility = View.GONE
        }

        if(card?.mechanics != null){
            card_details_mechanics_label.visibility = View.VISIBLE
            card_details_mechanics_label.text = getString(R.string.card_details_mechanics_label)


            card.mechanics.forEach {
                Log.e("mech",it.name)
                card_details_mechanics_label.text = card_details_mechanics_label.text.toString() + it.name + ","
            }
        }else {
            card_details_mechanics_label.visibility = View.GONE
        }



        val cardBox: Box<FavouriteCard> = ObjectBox.boxStore.boxFor()
        card?.cardId?.let {
            if (cardBox.query().equal(FavouriteCard_.cardId, card.cardId).build().count() > 0) {

            }
        }
    }

}
