package com.kapanen.hearthstoneassessment.ui.filtering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kapanen.hearthstoneassessment.databinding.FragmentFilteringBinding
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.ui.home.tab.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilteringFragment : Fragment() {

    @Inject
    lateinit var adapterDelegatesManager: AdapterDelegatesManager

    private var _binding: FragmentFilteringBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.filteringToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val filteringViewModel = ViewModelProvider(this)[FilteringViewModel::class.java]
        val adapter = ListAdapter(adapterDelegatesManager)
        binding.filteringRecyclerView.adapter = adapter
        binding.filteringRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.filteringRecyclerView.itemAnimator = null
        filteringViewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }
        filteringViewModel.prepareItems(resources)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
