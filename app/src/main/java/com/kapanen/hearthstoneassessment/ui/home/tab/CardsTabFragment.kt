package com.kapanen.hearthstoneassessment.ui.home.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.databinding.FragmentCardsTabBinding
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.NoDataItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARGS_KEY = "TAB"
private const val GRID_NUMBER_OF_COLUMNS = 3

@AndroidEntryPoint
class CardsTabFragment : Fragment() {

    @Inject
    lateinit var adapterDelegatesManager: AdapterDelegatesManager

    private var _binding: FragmentCardsTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cardsTabViewModel =
            ViewModelProvider(this)[CardsTabViewModel::class.java]
        binding.apply {
            val cardsListAdapter = CardsListAdapter(adapterDelegatesManager)
            val layoutManager = GridLayoutManager(requireContext(), GRID_NUMBER_OF_COLUMNS)
            cardsRecyclerView.adapter = cardsListAdapter
            cardsRecyclerView.layoutManager = layoutManager
            cardsRecyclerView.itemAnimator = null
            showLoading()
            (arguments?.get(ARGS_KEY) as CardsTab?)?.let { cardsTab ->
                cardsTabViewModel.loadCards(cardsTab).observe(viewLifecycleOwner) { cards ->
                    updateUi(cards, cardsListAdapter)
                }
            }
            cardsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val visibleItemCount: Int = layoutManager.childCount
                        val totalItemCount: Int = layoutManager.itemCount
                        val firstVisibleItem: Int = layoutManager.findFirstVisibleItemPosition()
                        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                            cardsListAdapter.setItems(cardsTabViewModel.addNextPage(totalItemCount))
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showCards() {
        binding.apply {
            binding.cardsNoDataContainer.noDataContainer.isVisible = false
            binding.cardsProgressContainer.progressContainer.isVisible = false
            cardsRecyclerView.isVisible = true
        }
    }

    private fun updateUi(
        cards: List<Card>,
        cardsListAdapter: CardsListAdapter
    ) {
        if (cards.isNotEmpty()) {
            showCards()
            cardsListAdapter.setItems(cards)
        } else {
            showNoData()
            cardsListAdapter.setItems(listOf(NoDataItem()))
        }
    }

    private fun showLoading() {
        binding.apply {
            binding.cardsNoDataContainer.noDataContainer.isVisible = false
            binding.cardsProgressContainer.progressContainer.isVisible = true
            cardsRecyclerView.isVisible = false
        }
    }

    private fun showNoData() {
        binding.apply {
            binding.cardsNoDataContainer.noDataContainer.isVisible = true
            binding.cardsProgressContainer.progressContainer.isVisible = false
            cardsRecyclerView.isVisible = false
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
