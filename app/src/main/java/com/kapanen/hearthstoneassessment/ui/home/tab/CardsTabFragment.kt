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
import com.kapanen.hearthstoneassessment.model.CardsTab
import dagger.hilt.android.AndroidEntryPoint

private const val ARGS_KEY = "TAB"
private const val GRID_NUMBER_OF_COLUMNS = 3

@AndroidEntryPoint
class CardsTabFragment : Fragment() {

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
        recyclerView.adapter =
        (arguments?.get(ARGS_KEY) as CardsTab?)?.let { cardsTab ->
            cardsTabViewModel.observeCards(cardsTab).observe(viewLifecycleOwner, { cards ->

            })
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
