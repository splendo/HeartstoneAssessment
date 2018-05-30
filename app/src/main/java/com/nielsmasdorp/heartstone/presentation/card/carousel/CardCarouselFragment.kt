package com.nielsmasdorp.heartstone.presentation.card.carousel

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nielsmasdorp.heartstone.R
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import android.support.transition.TransitionInflater
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewPager
import com.nielsmasdorp.heartstone.presentation.card.CardsActivity
import kotlinx.android.synthetic.main.fragment_card_detail.view.*
import kotlinx.android.synthetic.main.fragment_cards_carousel.*

class CardCarouselFragment : Fragment(), CardCarousel.View {

    @Inject
    lateinit var presenter: CardCarousel.Presenter

    override var cards: List<CardViewModel> = emptyList()
        set(value) {
            field = value
            carouselAdapter.cards = value
            cardsCarouselViewPager.currentItem = CardsActivity.CURRENT_POSITION
        }

    private val carouselAdapter: CardCarouselPagerAdapter by lazy {
        CardCarouselPagerAdapter(this)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cards_carousel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()

        prepareSharedElementTransition()

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        presenter.startPresenting()
    }

    private fun prepareSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition).apply {
            duration = resources.getInteger(R.integer.default_animation_length_ms).toLong()
        }
        setEnterSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        val currentFragment = cardsCarouselViewPager.adapter?.instantiateItem(cardsCarouselViewPager, CardsActivity.CURRENT_POSITION) as Fragment
                        val view = currentFragment.view ?: return

                        sharedElements.put(names[0], view.cardDetailImage)
                    }
                })
    }

    private fun initPager() {
        cardsCarouselViewPager.apply {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    // no op
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    // no op
                }

                override fun onPageSelected(position: Int) {
                    CardsActivity.CURRENT_POSITION = position
                }
            })
            adapter = carouselAdapter
        }
    }

    companion object {

        const val CARDS_KEY = "CARDS_KEY"

        /**
         * Create a new instance of this fragment
         * @param cards [ArrayList] of [CardViewModel]s that need to be shown in the carousel
         */
        fun newInstance(cards: ArrayList<CardViewModel>): CardCarouselFragment {
            return CardCarouselFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CARDS_KEY, cards)
                }
            }
        }
    }
}