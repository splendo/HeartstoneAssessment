package com.kapanen.hearthstoneassessment.ui.home.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.LoadingItem
import com.kapanen.hearthstoneassessment.model.NoDataItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARGS_KEY = "TAB"
private const val GRID_NUMBER_OF_COLUMNS = 3

@AndroidEntryPoint
class CardsTabFragment : Fragment() {

    @Inject
    lateinit var adapterDelegatesManager: AdapterDelegatesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cards_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cardsTabViewModel =
            ViewModelProvider(this)[CardsTabViewModel::class.java]
        val recyclerView = view.findViewById<RecyclerView>(R.id.cards_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_NUMBER_OF_COLUMNS)
        val cardsListAdapter = CardsListAdapter(adapterDelegatesManager)
        recyclerView.adapter = cardsListAdapter
        cardsListAdapter.setItems(listOf(LoadingItem()))
        (arguments?.get(ARGS_KEY) as CardsTab?)?.let { cardsTab ->
            cardsTabViewModel.observeCards(cardsTab).observe(viewLifecycleOwner) { cards ->
                if (cards.isNotEmpty()) {
                    cardsListAdapter.setItems(cards)
                } else {
                    cardsListAdapter.setItems(listOf(NoDataItem()))
                }
            }
        }
    }

    companion object {
        fun createFragment(cardsTab: CardsTab): CardsTabFragment {
            val fragment = CardsTabFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARGS_KEY, cardsTab)
            }
            return fragment
        }
    }

}
