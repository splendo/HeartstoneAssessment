package com.kapanen.hearthstoneassessment.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FragmentHomeBinding
import com.kapanen.hearthstoneassessment.ui.home.tab.CardsTabAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel.loadCards()

        val viewPager = binding.homeViewPager
        viewPager.adapter = CardsTabAdapter(this, homeViewModel.getCardTabs())
        TabLayoutMediator(binding.homeTabBar, viewPager) { tab, position ->
            tab.text = resources.getText(homeViewModel.getCardTabsLabelRes(position))
        }.attach()

        val menu = binding.homeToolbar.menu
        updateSortingIcon(menu, homeViewModel.isAscendingSorting())
        menu.findItem(R.id.menu_item_filtering)?.setOnMenuItemClickListener {
            findNavController(binding.root).navigate(
                HomeFragmentDirections.actionNavigationHomeToNavigationFiltering()
            )
            true
        }
        menu.findItem(R.id.menu_item_sorting)?.setOnMenuItemClickListener {
            homeViewModel.switchSortingOrder()
            updateSortingIcon(menu, homeViewModel.isAscendingSorting())
            true
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateSortingIcon(menu: Menu, isAscendingSorting: Boolean) {
        menu.findItem(R.id.menu_item_sorting)?.setIcon(
            if (isAscendingSorting) {
                R.drawable.ic_arrow_up
            } else {
                R.drawable.ic_arrow_down
            }
        )
    }

}
