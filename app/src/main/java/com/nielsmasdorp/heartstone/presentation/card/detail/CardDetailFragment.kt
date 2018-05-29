package com.nielsmasdorp.heartstone.presentation.card.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nielsmasdorp.heartstone.R
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_card_detail.*
import javax.inject.Inject
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class CardDetailFragment : Fragment(), CardDetail.View {

    @Inject
    lateinit var presenter: CardDetail.Presenter

    override var card: CardViewModel? = null
        set(value) {
            field = value
            value?.let {
                showCard(it)
            }
        }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardDetailFavoritesIcon.setOnClickListener { presenter.onAddToFavoritesClicked() }
        presenter.startPresenting()
    }

    override fun showCardAsFavorite(notifyUser: Boolean) {
        cardDetailFavoritesIcon.setImageResource(R.drawable.ic_favorites_filled_24dp)
        if (notifyUser) Toast.makeText(context, getString(R.string.card_details_added_favorites_message), Toast.LENGTH_SHORT).show()
    }

    override fun showCardNotFavorited() {
        cardDetailFavoritesIcon.setImageResource(R.drawable.ic_favorites_empty_24dp)
        Toast.makeText(context, getString(R.string.card_details_removed_favorites_message), Toast.LENGTH_SHORT).show()
    }

    private fun showCard(card: CardViewModel) {
        cardDetailImage.transitionName = card.name
        Glide.with(this)
                .load(card.imgUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(exception: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        parentFragment?.startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        parentFragment?.startPostponedEnterTransition()
                        return false
                    }
                })
                .into(cardDetailImage)
        cardDetailName.text = card.name
        cardDetailSet.text = card.cardSet
        cardDetailType.text = card.type
        cardDetailRarity.text = card.rarity
        cardDetailDescription.text = getTextAsHtml(card.text)
    }

    private fun getTextAsHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    companion object {

        const val CARD_KEY = "CARD_KEY"

        /**
         * Create a new instance of this fragment
         * @param card [CardViewModel] that needs to be shown in this fragment
         */
        fun newInstance(card: CardViewModel): CardDetailFragment {
            return CardDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CARD_KEY, card)
                }
            }
        }
    }
}