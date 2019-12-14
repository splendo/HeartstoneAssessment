package com.krayem.hearthstone.ui.main.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter


class SpinnerArrayAdapter(
    context: Context,
    private val resource: Int,
    private val objects: Array<String>,
    val selected: MutableSet<String>
) :
    ArrayAdapter<String>(context, resource, objects) {



    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: SpinnerViewHolder
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false)
            holder = SpinnerViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView.tag as SpinnerViewHolder
            view = convertView
        }
        val current = objects[position]
        holder.text.text = current
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = selected.contains(current)
        holder.checkBox.setOnCheckedChangeListener { _,isChecked ->
            if (isChecked) {
                selected.add(current)
            } else {
                selected.remove(current)
            }
        }
        holder.container.setOnClickListener { holder.checkBox.performClick() }
        return view

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDropDownView(position, convertView, parent)
    }
}