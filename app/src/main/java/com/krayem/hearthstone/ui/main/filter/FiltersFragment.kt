package com.krayem.hearthstone.ui.main.filter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout

import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.*

import kotlinx.android.synthetic.main.filters_fragment.*

class FiltersFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private lateinit var viewModel: FiltersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_fragment, container, false)
    }

    private fun handleSpinner(
        apiResponse: DefaultApiResponse,
        selectedItems: MutableSet<String>,
        label: String,
        autoCompleteTextView: AutoCompleteTextView,
        textInputLayout: TextInputLayout
    ) {
        when (apiResponse.status) {
            ResponseStatus.SUCCESS -> {
                autoCompleteTextView.isEnabled = true
                textInputLayout.isEnabled = true

                @Suppress("UNCHECKED_CAST")
                if (apiResponse is ListApiResponse<*>) {
                    val adapter = SpinnerArrayAdapter(
                        context!!,
                        R.layout.spinner_item,
                        (apiResponse.items as List<String>).toTypedArray(),
                        selectedItems
                    )
                    autoCompleteTextView.setAdapter(adapter)
                    autoCompleteTextView.setOnDismissListener {
                        autoCompleteTextView.setText(
                            String.format(
                                label,
                                selectedItems.size
                            ), false
                        )
                    }
                }
            }
            ResponseStatus.ERROR -> {
                autoCompleteTextView.isEnabled = false
                textInputLayout.isEnabled = false
            }
            ResponseStatus.LOADING -> {
                autoCompleteTextView.isEnabled = false
                textInputLayout.isEnabled = false
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FiltersViewModel::class.java)

        viewModel.typesResponse.observe(this, Observer {
            handleSpinner(
                it,
                selectedTypes,
                getString(R.string.type_selected_label),
                type_tv,
                type_til
            )
        })

        viewModel.classesResponse.observe(this, Observer {
            handleSpinner(
                it,
                selectedClasses,
                getString(R.string.class_selected_label),
                class_tv,
                class_til
            )
        })

        viewModel.mechanicsResponse.observe(this, Observer {
            handleSpinner(
                it,
                selectedMechanics,
                getString(R.string.mechanic_selected_label),
                mechanic_tv,
                mechanic_til
            )
        })

        viewModel.filtersResponse.observe(this, Observer {
            selectedTypes.addAll(it.types)
            selectedClasses.addAll(it.classes)
            selectedMechanics.addAll(it.mechanics)
            type_tv.setText(
                String.format(
                    getString(R.string.type_selected_label),
                    selectedTypes.size
                ), false
            )
            class_tv.setText(
                String.format(
                    getString(R.string.class_selected_label),
                    selectedClasses.size
                ), false
            )
            mechanic_tv.setText(
                String.format(
                    getString(R.string.mechanic_selected_label),
                    selectedMechanics.size
                ), false
            )

            rarity_seek_bar.setProgress(it.minRarity.toFloat(),it.maxRarity.toFloat())
            if(it.sortBy == SectionFilter.SORT_BY_ALPHABETICAL){
                sort_alphabetic_rb.isChecked = true
                sort_alphabetic_cb.isChecked = it.descending
            }else{
                sort_rarity_rb.isChecked = true
                sort_rarity_cb.isChecked = it.descending
            }
        })

        viewModel.getTypes()
        viewModel.getClasses()
        viewModel.getMechanics()
        viewModel.getFilters()

    }

    var selectedTypes: MutableSet<String> = mutableSetOf()
    var selectedClasses: MutableSet<String> = mutableSetOf()
    var selectedMechanics: MutableSet<String> = mutableSetOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filters_toolbar.setNavigationIcon(R.drawable.ic_back)
        filters_toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        sort_alphabetic_rb.setOnCheckedChangeListener(this)
        sort_rarity_rb.setOnCheckedChangeListener(this)

        apply_filters_button.setOnClickListener { applyFilters() }
        clear_filters_button.setOnClickListener { clearFilters() }

    }

    private fun clearFilters() {
        selectedTypes.clear()
        selectedClasses.clear()
        selectedMechanics.clear()
        viewModel.clearFilters()
        mechanic_tv.setText(
            String.format(
                getString(R.string.mechanic_selected_label),
                selectedMechanics.size
            ), false
        )
        class_tv.setText(
            String.format(
                getString(R.string.class_selected_label),
                selectedClasses.size
            ), false
        )
        type_tv.setText(
            String.format(
                getString(R.string.type_selected_label),
                selectedTypes.size
            ), false
        )
        rarity_seek_bar.setProgress(0f,4f)
        sort_alphabetic_cb.isChecked = false
        sort_rarity_cb.isChecked = false
        sort_alphabetic_rb.isChecked = true
    }

    private fun applyFilters() {
        viewModel.saveFilters(
            selectedTypes.toList(),
            selectedMechanics.toList(),
            selectedClasses.toList(),
            rarity_seek_bar.leftSeekBar.progress.toInt(),
            rarity_seek_bar.rightSeekBar.progress.toInt(),
            if (sort_alphabetic_rb.isChecked) SectionFilter.SORT_BY_ALPHABETICAL else SectionFilter.SORT_BY_RARITY,
            if (sort_alphabetic_rb.isChecked) sort_alphabetic_cb.isChecked else sort_rarity_cb.isChecked
        )
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            radioButtonIds.forEach {
                if (it != buttonView?.id) {
                    filters_root.findViewById<RadioButton>(it).isChecked = false
                }
            }
        }
    }

    private val radioButtonIds = listOf(
        R.id.sort_alphabetic_rb,
        R.id.sort_rarity_rb
    )

}
