package com.kapanen.hearthstoneassessment.ui.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
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
        (activity as AppCompatActivity).setSupportActionBar(binding.cardDetailsToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        updateCardArrows(position = 0, itemsCount = 0)
        cardDetailsViewModel.cardsLiveData.observe(viewLifecycleOwner) { cards ->
            viewPager.adapter = CardViewPagerAdapter(this, cards)
            cards.indexOfFirst { card -> card.cardId == cardId }
                .takeIf { it > 0 }
                ?.let { index ->
                    updateCardArrows(position = index, itemsCount = cards.size)
                    viewPager.currentItem = index
                } ?: updateCardArrows(position = 0, itemsCount = cards.size)
            binding.cardDetailsArrowBack.setOnClickListener {
                if (viewPager.currentItem > 0) {
                    viewPager.currentItem = viewPager.currentItem - 1
                }
            }
            binding.cardDetailsArrowForward.setOnClickListener {
                val itemCount = viewPager.adapter?.itemCount ?: 0
                if (itemCount > 0 && viewPager.currentItem != itemCount - 1) {
                    viewPager.currentItem = viewPager.currentItem + 1
                }
            }
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateCardArrows(
                    position = position,
                    itemsCount = viewPager.adapter?.itemCount ?: 0
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCardArrows(position: Int, itemsCount: Int) {
        binding.cardDetailsArrowBack.isVisible = position > 0
        binding.cardDetailsArrowForward.isVisible = itemsCount > 0 && position != itemsCount - 1
    }

}
