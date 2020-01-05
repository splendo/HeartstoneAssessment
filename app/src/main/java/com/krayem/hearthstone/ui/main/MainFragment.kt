package com.krayem.hearthstone.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.krayem.hearthstone.R
import com.krayem.hearthstone.ui.main.filter.FiltersFragment
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardSets = resources.getStringArray(R.array.card_sets_array)
        fragment_main_tablayout.setupWithViewPager(fragment_main_viewpager)
        val viewPagerAdapter = CardSetPagerAdapter(childFragmentManager, cardSets)
        fragment_main_viewpager.adapter = viewPagerAdapter
        fragment_main_toolbar.menu.findItem(R.id.filters_button).setOnMenuItemClickListener {
            showFiltersFragment()
            true
        }

    }


    private fun showFiltersFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )?.replace(R.id.activity_main_root, FiltersFragment.newInstance())?.addToBackStack(null)?.commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        // TODO: Use the ViewModel


//        viewModel.allResponse.observe(this, Observer {
//            when(it.status){
//                ReponseStatus.SUCCESS -> {
//                    @Suppress("UNCHECKED_CAST")
//                    if(it is ListApiResponse<*>){
//                        list_fragment_progress.visibility = View.GONE
//                        list_fragment_cards_rv.visibility = View.VISIBLE
//                        adapter.replaceAll(it.items as List<Card>)
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//                ReponseStatus.LOADING -> showLoading()
//                ReponseStatus.ERROR -> showError(it.errorMessage)
//            }
//
//        })
//        val cardSet = arguments?.getString(CARDSET_INTENT_EXTRA)
//        cardSet?.let { viewModel.getCardSet(it) }
    }


}
