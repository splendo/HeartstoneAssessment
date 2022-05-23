package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kapanen.hearthstoneassessment.model.CardsTab

class CardsTabAdapter(fragment: Fragment, private val tabs: List<CardsTab>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return CardsTabFragment.createFragment(tabs[position])
    }

}
