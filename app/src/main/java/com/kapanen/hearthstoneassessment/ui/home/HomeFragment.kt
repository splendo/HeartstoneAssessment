package com.kapanen.hearthstoneassessment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
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

        val viewPager = binding.homeViewPager
        viewPager.adapter = CardsTabAdapter(this, homeViewModel.getCardTabs())
        TabLayoutMediator(binding.homeTabBar, viewPager) { tab, position ->
            tab.text = resources.getText(homeViewModel.getCardTabsLabelRes(position))
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
