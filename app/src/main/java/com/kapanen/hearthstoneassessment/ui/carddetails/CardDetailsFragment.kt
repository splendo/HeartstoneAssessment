package com.kapanen.hearthstoneassessment.ui.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FragmentCardDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsFragment : Fragment() {

    private var _binding: FragmentCardDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardDetailsViewModel =
            ViewModelProvider(this)[CardDetailsViewModel::class.java]
        val viewPager = binding.cardDetailsViewPager
        val cardId = arguments?.getString(resources.getString(R.string.arg_card_id))
        val cardType = arguments?.getString(resources.getString(R.string.arg_card_type))
        cardId?.let { cardDetailsViewModel.init(cardId = it, cardType = cardType) }
        cardDetailsViewModel.cardsLiveData.observe(viewLifecycleOwner) { cards ->
            viewPager.adapter = CardViewPagerAdapter(this, cards)
            cards.indexOfFirst { card -> card.cardId == cardId }
                .takeIf { it > 0 }
                ?.let { index -> viewPager.currentItem = index }
        }
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
