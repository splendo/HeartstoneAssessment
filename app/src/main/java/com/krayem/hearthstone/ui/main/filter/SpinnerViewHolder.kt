package com.krayem.hearthstone.ui.main.filter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.spinner_item.view.*

class SpinnerViewHolder(view: View) {

    val text:TextView  = view.spinner_item_tv
    val checkBox:CheckBox = view.spinner_item_checkbox
    val container:ViewGroup = view.spinner_item_container
}