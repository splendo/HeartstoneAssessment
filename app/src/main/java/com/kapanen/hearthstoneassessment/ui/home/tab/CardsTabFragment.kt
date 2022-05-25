package com.kapanen.hearthstoneassessment.ui.home.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kapanen.hearthstoneassessment.databinding.FragmentCardsTabBinding
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
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
            cardsRecyclerView.adapter = cardsListAdapter
            cardsRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), GRID_NUMBER_OF_COLUMNS)
            cardsRecyclerView.itemAnimator = null
            showLoading()
            (arguments?.get(ARGS_KEY) as CardsTab?)?.let { cardsTab ->
                cardsTabViewModel.observeCards(cardsTab).observe(viewLifecycleOwner) { cards ->
                    if (cards.isNotEmpty()) {
                        showCards()
                        cardsListAdapter.setItems(cards)
                    } else {
                        showNoData()
                        cardsListAdapter.setItems(listOf(NoDataItem()))
                    }
                }
            }
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
