package com.kapanen.hearthstoneassessment.model

data class FilterItem(
    val label: String,
    val value: String,
    val filterType: FilterType,
    val isEnabled: Boolean = false
) {
    companion object {
        const val UNDEFINED = "UNDEFINED"
    }
}
