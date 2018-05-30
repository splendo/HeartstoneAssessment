package com.nielsmasdorp.heartstone.presentation.card.carousel

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetailFragment

/**
 * Adapter class for the card carousel
 */
class CardCarouselPagerAdapter(fragment: Fragment) : FragmentStatePagerAdapter(fragment.childFragmentManager) {

    var cards: List<CardViewModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = cards.count()

    override fun getItem(position: Int): Fragment {
        return CardDetailFragment.newInstance(cards[position])
    }
}