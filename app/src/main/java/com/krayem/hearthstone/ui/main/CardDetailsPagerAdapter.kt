package com.krayem.hearthstone.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.krayem.hearthstone.ui.main.filter.FiltersFragment

class CardDetailsPagerAdapter(fragmentManager: FragmentManager, private val items: ArrayList<Int>) :
    FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // 2
    override fun getItem(position: Int): Fragment {
        return FiltersFragment.newInstance()
    }

    // 3
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Info"
            1 -> return "Mechanics"
        }
        return super.getPageTitle(position)
    }
}