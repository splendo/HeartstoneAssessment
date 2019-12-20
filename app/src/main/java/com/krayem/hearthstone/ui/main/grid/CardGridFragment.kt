package com.krayem.hearthstone.ui.main.grid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Fade
import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.ListApiResponse
import com.krayem.hearthstone.model.ResponseStatus
import com.krayem.hearthstone.ui.main.details.CardDetailsFragment
import com.krayem.hearthstone.utils.ImageTransition
import com.krayem.hearthstone.ui.main.grid.recyclerview.CardGridAdapter
import com.krayem.hearthstone.utils.CARDSET_INTENT_EXTRA
import com.krayem.hearthstone.utils.FAVOURITES_INTENT_EXTRA
import kotlinx.android.synthetic.main.card_grid_fragment.*


class CardGridFragment : Fragment() {


    companion object {
        fun newInstance(cardSet: String, isFavourite: Boolean): CardGridFragment {
            val fragment = CardGridFragment()
            val args = Bundle()
            args.putString(CARDSET_INTENT_EXTRA, cardSet)
            args.putBoolean(FAVOURITES_INTENT_EXTRA, isFavourite)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: CardGridViewModel

    private lateinit var adapter: CardGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.card_grid_fragment, container, false)
    }

    private fun showDetailsFragment(card: Card, image: ImageView) {
        val fragment = CardDetailsFragment.newInstance()
        val bundle = Bundle()
        bundle.putParcelable("CARD_TEST", card)
        bundle.putParcelable("BITMAP_TEST", image.drawable.toBitmap())
        fragment.arguments = bundle
        fragment.sharedElementEnterTransition =
            ImageTransition()
        fragment.enterTransition = Fade()
        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
            0,
            0,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
            ?.addSharedElement(image, "sharedImage" + card.cardId)
            ?.replace(R.id.activity_main_root, fragment)?.addToBackStack(null)?.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view.context)
    }


    private fun setupRecyclerView(context: Context) {

        adapter = CardGridAdapter(
            context,
            ArrayList()
        ) { card: Card, image: ImageView -> showDetailsFragment(card, image) }

        list_fragment_cards_rv.layoutManager = GridLayoutManager(context, 3)
        list_fragment_cards_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )

        list_fragment_cards_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        list_fragment_cards_rv.adapter = adapter
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardGridViewModel::class.java)




        viewModel.cardSetResponse.observe(this, Observer {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    @Suppress("UNCHECKED_CAST")
                    if (it is ListApiResponse<*>) {
                        list_fragment_progress.visibility = View.GONE
                        if (it.items.isEmpty()) {
                            list_fragment_cards_rv.visibility = View.GONE
                            list_fragment_empty_label.visibility = View.VISIBLE
                            list_fragment_empty_label.text = getString(R.string.no_cards_here)
                        } else {
                            list_fragment_cards_rv.visibility = View.VISIBLE
                            list_fragment_empty_label.visibility = View.GONE
                        }
                        adapter.replaceAll(it.items as List<Card>)
                    }
                }
                ResponseStatus.LOADING -> showLoading()
                ResponseStatus.ERROR -> showError(it.errorMessage)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        val cardSet = arguments?.getString(CARDSET_INTENT_EXTRA)
        val isFavourite = arguments?.getBoolean(FAVOURITES_INTENT_EXTRA)
        if (isFavourite != null && isFavourite) {
            viewModel.getFavourites()
        } else {
            cardSet?.let { viewModel.getCardSet(it) }
        }

    }


    private fun showLoading() {
        list_fragment_progress.visibility = View.VISIBLE
        list_fragment_empty_label.visibility = View.VISIBLE
        list_fragment_empty_label.text = getString(R.string.loading)
        list_fragment_cards_rv.visibility = View.GONE
    }

    private fun showError(message: String?) {
        list_fragment_progress.visibility = View.GONE
        list_fragment_cards_rv.visibility = View.GONE
        list_fragment_empty_label.visibility = View.VISIBLE
        list_fragment_empty_label.text = "ERROR"
        message?.let { Log.e("error", message) }
        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
    }

}
