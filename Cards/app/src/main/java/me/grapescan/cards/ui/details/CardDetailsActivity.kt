package me.grapescan.cards.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_card_details.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.ParcelableExtra
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CardDetailsActivity : AppCompatActivity() {

    companion object {

        private var Intent.initialCard by ParcelableExtra<Card>()

        private fun <T> MutableLiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) =
            this.observe(lifecycleOwner, Observer { observer(it) })

        fun createIntent(context: Context, selectedCard: Card) =
            Intent(context, CardDetailsActivity::class.java).apply {
                this.initialCard = selectedCard
            }
    }

    private val viewModel: CardDetailsViewModel by inject(parameters = { parametersOf(intent.initialCard) })
    private val cardDetailsAdapter = CardDetailsAdapter(object : CardDetailsAdapter.ContentLoadingListener {
        override fun onLoadingSuccess(card: Card) = onLoadingComplete(card)

        override fun onLoadingError(card: Card) = onLoadingComplete(card)

        private fun onLoadingComplete(card: Card) {
            if (card.id == intent.initialCard.id) {
                // This delay is required for view pager to scroll to current page.
                contentPager.postDelayed({ supportStartPostponedEnterTransition() }, 300)
            }
        }
    })
    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
        set(value) {
            field?.let {
                contentPager.unregisterOnPageChangeCallback(it)
            }
            field = value
            field?.let {
                contentPager.registerOnPageChangeCallback(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        back.setOnClickListener { supportFinishAfterTransition() }
        contentPager.run {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = cardDetailsAdapter
        }
        supportPostponeEnterTransition()

        viewModel.observe()
    }

    private fun CardDetailsViewModel.observe() {
        cards.observe(this@CardDetailsActivity, ::onCardsUpdate)
        currentCard.observe(this@CardDetailsActivity, ::onCurrentCardUpdate)
    }

    private fun onCurrentCardUpdate(currentCard: Card) {
        contentPager.currentItem = cardDetailsAdapter.currentList.indexOfFirst { it.id == currentCard.id }
        favorite.setCheckedSilent(currentCard.isFavorite)
        favorite.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setFavorite(currentCard.id, isChecked)
        }
    }

    private fun onCardsUpdate(cards: List<Card>) {
        cardDetailsAdapter.submitList(cards)
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onCardSwitch(cards[position])
            }
        }
    }
}
