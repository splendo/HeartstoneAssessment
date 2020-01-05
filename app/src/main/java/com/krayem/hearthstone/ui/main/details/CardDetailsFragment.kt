package com.krayem.hearthstone.ui.main.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.Card
import kotlinx.android.synthetic.main.card_details_fragment.*

class CardDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            CardDetailsFragment()
    }

    private lateinit var viewModel: CardDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_details_fragment, container, false)
    }

    var card: Card? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        card = arguments?.getParcelable("CARD_TEST")
        viewModel.favouriteResponse.observe(this, Observer {
            if (it) {
                card_details_favourite_fab.setImageResource(R.drawable.ic_favourite_checked_white)
            } else {
                card_details_favourite_fab.setImageResource(R.drawable.ic_favourite_unchecked_white)
            }
            card_details_favourite_fab.setOnClickListener {
                card?.cardId?.let { it1 -> viewModel.toggleFavourite(it1) }
            }
        })

        viewModel.classesMechanicsResponse.observe(this, Observer {

            card_details_fragment_rv.adapter =
                context?.let { it1 -> CardDetailsSectionsAdapter(it1, getListItemsFromCard(card,it.first,it.second)) }
        })

        card?.cardId?.let {
            viewModel.getClassesAndMechanics(it)
            viewModel.checkIsFavourite(it)
        }
    }

    private fun getListItemsFromCard(
        card: Card?,
        classes: Array<String>,
        mechanics: Array<String>
    ): ArrayList<Pair<String, String>> {
        val items = arrayListOf<Pair<String, String>>()
        if (card != null) {
            items.add(Pair(getString(R.string.card_details_name_label), card.name))
            if (card.cardSet != null) {
                items.add(Pair(getString(R.string.card_details_set_label), card.cardSet))
            }
            if (card.type != null) {
                items.add(Pair(getString(R.string.card_details_type_label), card.type))
            }
            if (card.rarity != null) {
                items.add(Pair(getString(R.string.card_details_rarity_label), card.rarity))
            }
            if (card.cost != null) {
                items.add(Pair(getString(R.string.card_details_cost_label), card.cost.toString()))
            }
            if (card.attack != null) {
                items.add(
                    Pair(
                        getString(R.string.card_details_attack_label),
                        card.attack.toString()
                    )
                )
            }
            if (card.health != null) {
                items.add(
                    Pair(
                        getString(R.string.card_details_health_label),
                        card.health.toString()
                    )
                )
            }


            if (card.text != null) {
                items.add(Pair(getString(R.string.card_details_text_label), card.text))
            }
            if (card.flavor != null) {
                items.add(Pair(getString(R.string.card_details_flavor_label), card.flavor))
            }
            if (card.playerClass != null) {
                items.add(
                    Pair(
                        getString(R.string.card_details_player_class_label),
                        card.playerClass
                    )
                )
            }
            if (card.multiClassGroup != null) {
                items.add(
                    Pair(
                        getString(R.string.card_details_multi_class_group_label),
                        card.multiClassGroup
                    )
                )
            }

            if(classes.isNotEmpty()){
                items.add(
                    Pair(
                        getString(R.string.card_details_classes_label)
                        ,getLabelFromArray(classes)
                    )
                )
            }

            if(mechanics.isNotEmpty()){
                items.add(
                    Pair(
                        getString(R.string.card_details_mechanics_label)
                        ,getLabelFromArray(mechanics)
                    )
                )
            }
        }
        return items
    }

    private fun getLabelFromArray(items:Array<String>):String{
        var toReturn = ""
        items.forEach {
            toReturn = toReturn.plus(it).plus(", ")
        }
        toReturn = if(toReturn.isEmpty()){
            "-"
        }else{
            toReturn.removeSuffix(", ")
        }
        return toReturn
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card: Card? = arguments?.getParcelable("CARD_TEST")
//            .placeholder(R.drawable.placeholder_card)
//        Picasso.get().load(card?.img).error(R.drawable.placeholder_card).into(htab_header)
//
        card_details_fragment_image.setImageBitmap(arguments?.getParcelable("BITMAP_TEST"))
        ViewCompat.setTransitionName(card_details_fragment_image, "sharedImage" + card?.cardId)
        card_details_fragment_toolbar.title = card?.name
        card_details_fragment_toolbar.setNavigationIcon(R.drawable.ic_back)
        card_details_fragment_toolbar.setNavigationOnClickListener { activity?.supportFragmentManager?.popBackStack() }

        card_details_fragment_rv.layoutManager = LinearLayoutManager(view.context)
        card_details_fragment_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


        if (card?.artist != null) {
            card_details_fragment_artist_label.text =
                String.format(getString(R.string.card_details_artist_label), card.artist)
            card_details_fragment_artist_label.visibility = View.VISIBLE

        } else {
            card_details_fragment_artist_label.visibility = View.GONE
        }
    }

}
