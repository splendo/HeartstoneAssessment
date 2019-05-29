package me.grapescan.cards.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_card_preview.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.ParcelableExtra
import me.grapescan.cards.ui.info.CardInfoActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CardPreviewActivity : AppCompatActivity() {

    companion object {

        private var Intent.initialCard by ParcelableExtra<Card>()

        private fun <T> MutableLiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) =
            this.observe(lifecycleOwner, Observer { observer(it) })

        fun createIntent(context: Context, selectedCard: Card) =
            Intent(context, CardPreviewActivity::class.java).apply {
                this.initialCard = selectedCard
            }
    }

    private val viewModel: CardPreviewViewModel by inject(parameters = { parametersOf(intent.initialCard) })
    private val cardPreviewAdapter = CardPreviewAdapter(object : CardPreviewAdapter.ContentLoadingListener {
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
        setContentView(R.layout.activity_card_preview)
        back.setOnClickListener { supportFinishAfterTransition() }
        contentPager.run {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = cardPreviewAdapter
        }
        supportPostponeEnterTransition()

        viewModel.observe()
    }

    private fun CardPreviewViewModel.observe() {
        cards.observe(this@CardPreviewActivity, ::onCardsUpdate)
        currentCard.observe(this@CardPreviewActivity, ::onCurrentCardUpdate)
    }

    private fun onCurrentCardUpdate(currentCard: Card) {
        contentPager.currentItem = cardPreviewAdapter.currentList.indexOfFirst { it.id == currentCard.id }
        favorite.setCheckedSilent(currentCard.isFavorite)
        favorite.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setFavorite(currentCard.id, isChecked)
        }
        info.setOnClickListener { startActivity(CardInfoActivity.createIntent(this, currentCard)) }
    }

    private fun onCardsUpdate(cards: List<Card>) {
        cardPreviewAdapter.submitList(cards)
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onCardSwitch(cards[position])
            }
        }
    }
}
