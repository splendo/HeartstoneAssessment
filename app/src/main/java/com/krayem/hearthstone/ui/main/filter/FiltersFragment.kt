package com.krayem.hearthstone.ui.main.filter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import com.krayem.hearthstone.R
import kotlinx.android.synthetic.main.filters_fragment.*

class FiltersFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FiltersViewModel::class.java)
        // TODO: Use the ViewModel
    }

    var selectedTypes :MutableSet<String> = mutableSetOf()
    var selectedClasses :MutableSet<String> = mutableSetOf()
    var selectedMechanics: MutableSet<String> = mutableSetOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filters_toolbar.setNavigationIcon(R.drawable.ic_back)
        filters_toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        filters_toolbar.menu?.findItem(R.id.filters_button)?.setOnMenuItemClickListener {
            applyFilter()
            true
        }


        val types = arrayOf(
            getString(R.string.type_minion_label),
            getString(R.string.type_spell_label),
            getString(R.string.type_weapon_label),
            getString(R.string.type_hero_label)
        )
        val classes = arrayOf(
            getString(R.string.class_druid_label),
            getString(R.string.class_hunter_label),
            getString(R.string.class_mage_label),
            getString(R.string.class_paladin_label),
            getString(R.string.class_priest_label),
            getString(R.string.class_rogue_label),
            getString(R.string.class_shaman_label),
            getString(R.string.class_warlock_label),
            getString(R.string.class_warrior_label)
        )

        val mechanics = arrayOf(
            getString(R.string.ability_adapt_label),
            getString(R.string.ability_battlecry_label),
            getString(R.string.ability_casts_when_drawn_label),
            getString(R.string.ability_charge_label),
            getString(R.string.ability_choose_one_label),
            getString(R.string.ability_choose_twice_label),
            getString(R.string.ability_combo_label),
            getString(R.string.ability_counter_label),
            getString(R.string.ability_deathrattle_label)
        )

        val typesAdapter = SpinnerArrayAdapter(context!!, R.layout.spinner_item, types,selectedTypes)
        type_tv.setAdapter(typesAdapter)
        type_tv.setOnDismissListener {
            type_tv.setText(String.format(getString(R.string.type_selected_label),selectedTypes.size),false)
        }

        val classesAdapter = SpinnerArrayAdapter(context!!, R.layout.spinner_item, classes,selectedClasses)
        class_tv.setAdapter(classesAdapter)
        class_tv.setOnDismissListener {
            class_tv.setText(String.format(getString(R.string.class_selected_label),selectedClasses.size),false)
        }

        val mechanicsAdapter = SpinnerArrayAdapter(context!!, R.layout.spinner_item, mechanics,selectedMechanics)
        mechanic_tv.setAdapter(mechanicsAdapter)
        mechanic_tv.setOnDismissListener {
            mechanic_tv.setText(String.format(getString(R.string.mechanic_selected_label),selectedMechanics.size),false)
        }
    }

    private fun applyFilter() {

    }

}
