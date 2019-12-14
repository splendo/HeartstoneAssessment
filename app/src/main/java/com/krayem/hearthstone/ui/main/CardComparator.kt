package com.krayem.hearthstone.ui.main

import com.krayem.hearthstone.model.Card

class CardComparator : Comparator<Card> {

    override fun compare(a: Card?, b: Card?): Int {
        return when {
            a?.name != null && b?.name != null -> a.name.compareTo(b.name)
            else -> 0
        }
    }
}