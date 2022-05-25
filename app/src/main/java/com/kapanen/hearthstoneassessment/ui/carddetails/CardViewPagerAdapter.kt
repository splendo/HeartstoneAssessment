package com.kapanen.hearthstoneassessment.ui.carddetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kapanen.hearthstoneassessment.model.Card

class CardViewPagerAdapter(fragment: Fragment, private val cards: List<Card>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = cards.size

    override fun createFragment(position: Int): Fragment {
        return CardFragment.createFragment(cards[position])
    }

}
