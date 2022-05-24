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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {
        }

        val viewPager = binding.homeViewPager
        viewPager.adapter = CardsTabAdapter(this, homeViewModel.getCardTabs())
        TabLayoutMediator(binding.homeTabBar, viewPager) { tab, position ->
            tab.text = resources.getText(homeViewModel.getCardTabsLabelRes(position))
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
