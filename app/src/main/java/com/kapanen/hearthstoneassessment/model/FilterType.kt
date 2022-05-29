package com.kapanen.hearthstoneassessment.model

import androidx.annotation.StringRes
import com.kapanen.hearthstoneassessment.R

enum class FilterType(@StringRes val filterLabelRes: Int) {
    TYPE(R.string.filter_header_type),
    RARITY(R.string.filter_header_rarity),
    CLASS(R.string.filter_header_class),
    MECHANIC(R.string.filter_header_mechanic)
}
