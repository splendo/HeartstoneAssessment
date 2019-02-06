package laurens.hearthstoneassessment.activities.detailpage

import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import kotlinx.android.synthetic.main.activity_detail_page.*
import laurens.hearthstoneassessment.R
import laurens.hearthstoneassessment.application.HearthstoneAssessment
import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.model.CardStatus
import laurens.hearthstoneassessment.utils.SwipeTouchListener
import laurens.hearthstoneassessment.utils.centerFitInsideWithPlaceHolder
import javax.inject.Inject

class DetailPageActivity : AppCompatActivity(), DetailPageContract.View, Observer<CardStatus> {

    @Inject
    lateinit var presenter: DetailPageContract.Presenter
    private var current: CardModel? = null
    private var favoriteIsChecked = false
        set(value) {
            field = value
            val imageResource = if (value) android.R.drawable.star_big_on else android.R.drawable.star_big_off
            toggle_favorite.setImageResource(imageResource)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_page)
        (applicationContext as HearthstoneAssessment).mainComponent.inject(this)
        presenter.initialize(this)
        registerListeners()

    }

    private fun registerListeners() {
        toggle_favorite.setOnClickListener { presenter.setFavoriteStatus(!favoriteIsChecked) }
        detail_view.setOnTouchListener(
            SwipeTouchListener(
                this,
                onSwipeRight = presenter::onSwipeRight,
                onSwipeLeft = presenter::onSwipeLeft
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun setContent(card: CardModel) {
        setupNewCard(card)
        detail_image.centerFitInsideWithPlaceHolder(card.card.img)
        card_title.text = card.card.name
        detail_text.text = fromHtml(card.card.text ?: "")
        card.status.observe(this, this)
    }

    private fun setupNewCard(card: CardModel) {
        clearCurrentCard()
        current = card
    }

    private fun clearCurrentCard() {
        current?.status?.removeObservers(this)
        this.favoriteIsChecked = false
    }

    @SuppressWarnings("deprecation")
    private fun fromHtml(text: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(text);
    }

    override fun onChanged(status: CardStatus?) {
        val favorite = status?.favorite ?: return
        this.favoriteIsChecked = favorite
    }
}
