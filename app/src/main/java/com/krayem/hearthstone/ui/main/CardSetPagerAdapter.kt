package com.krayem.hearthstone.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.krayem.hearthstone.ui.main.grid.CardGridFragment

class CardSetPagerAdapter(fragmentManager: FragmentManager, private val items: Array<String>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return CardGridFragment.newInstance(items[position],position == 0)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return items[position]
    }


}