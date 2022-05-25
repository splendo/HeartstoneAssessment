package com.kapanen.hearthstoneassessment.ui.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FragmentCardDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsFragment : Fragment() {

    private var _binding: FragmentCardDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardDetailsViewModel =
            ViewModelProvider(this)[CardDetailsViewModel::class.java]
        _binding = FragmentCardDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setSupportActionBar(binding.cardDetailsToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}