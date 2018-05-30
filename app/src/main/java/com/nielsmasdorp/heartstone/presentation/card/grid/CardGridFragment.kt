package com.nielsmasdorp.heartstone.presentation.card.grid

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nielsmasdorp.heartstone.R
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import android.view.View.OnLayoutChangeListener
import com.nielsmasdorp.heartstone.presentation.card.CardsActivity
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarouselFragment
import kotlinx.android.synthetic.main.grid_item_card.view.*

/**
 * Fragment used to display a grid of cards
 */
class CardGridFragment : Fragment(), CardGrid.View, CardGrid.Navigator, CardGrid.StringProvider {

    @Inject
    lateinit var presenter: CardGrid.Presenter

    override var cards: List<CardViewModel> = emptyList()
        set(value) {
            field = value
            cardGridAdapter.cards = value
            scrollToPosition()
        }

    private lateinit var cardGridAdapter: CardGridAdapter

    lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = inflater.inflate(R.layout.fragment_cards_grid, container, false) as RecyclerView
        cardGridAdapter = CardGridAdapter(this).apply {
            onCardClickedListener = ::onCardClicked
            onClickedImageLoaded = { startPostponedEnterTransition() }
        }
        recyclerView.adapter = cardGridAdapter

        prepareTransitions()
        postponeEnterTransition()

        return recyclerView
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting()
    }

    override fun onStop() {
        presenter.stopPresenting()
        super.onStop()
    }

    override fun openCardDetails(cardViewModel: CardViewModel) {
        val clickedPosition = cardGridAdapter.getPositionForViewModel(cardViewModel)
        CardsActivity.CURRENT_POSITION = clickedPosition
        val sharedTransitionView = recyclerView.layoutManager.findViewByPosition(clickedPosition)
        (exitTransition as TransitionSet).excludeTarget(sharedTransitionView, true)
        fragmentManager?.apply {
            beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(sharedTransitionView.cardGridImage, sharedTransitionView.cardGridImage.transitionName)
                    .replace(R.id.fragmentContainer, CardCarouselFragment.newInstance(cards as ArrayList<CardViewModel>), CardCarouselFragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun getUnknownAttributeString(): String = resources.getString(R.string.unknown)

    private fun onCardClicked(clickedViewModel: CardViewModel) {
        presenter.openCardDetails(clickedViewModel)
    }

    private fun scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(v: View,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int) {
                recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView.layoutManager
                val viewAtPosition = layoutManager.findViewByPosition(CardsActivity.CURRENT_POSITION)
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recyclerView.post({ layoutManager.scrollToPosition(CardsActivity.CURRENT_POSITION) })
                }
            }
        })
    }

    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition).apply {
            duration = resources.getInteger(R.integer.default_animation_length_ms).toLong()
        }
        setExitSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        recyclerView.findViewHolderForAdapterPosition(CardsActivity.CURRENT_POSITION)?.let {
                            sharedElements.put(names[0], it.itemView.cardGridImage)
                        }
                    }
                })
    }
}