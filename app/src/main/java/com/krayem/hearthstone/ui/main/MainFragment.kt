package com.krayem.hearthstone.ui.main

import android.os.Bundle
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
import com.krayem.hearthstone.model.ReponseStatus
import com.krayem.hearthstone.ui.main.filter.FiltersFragment
import com.krayem.hearthstone.ui.main.recyclerview.CardGridAdapter
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: CardGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun showDetailsFragment(card: Card, image: ImageView) {
        val fragment = CardDetailsFragment.newInstance()
        val bundle = Bundle()
        bundle.putParcelable("CARD_TEST", card)
        bundle.putParcelable("BITMAP_TEST", image.drawable.toBitmap())
        fragment.arguments = bundle
        fragment.sharedElementEnterTransition = ImageTransition()
        fragment.enterTransition = Fade()
        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
            0,
            0,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
            ?.addSharedElement(image, "sharedImage" + card.cardId)
            ?.replace(R.id.container, fragment)?.addToBackStack(null)?.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CardGridAdapter(
            view.context,
            ArrayList()
        ) { card: Card, image: ImageView -> showDetailsFragment(card, image) }
        cards_rv.layoutManager = GridLayoutManager(context, 3)
        cards_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )
        cards_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        main_toolbar.title = getString(R.string.app_name)
        main_toolbar.menu?.findItem(R.id.filters_button)?.setOnMenuItemClickListener {
            showFiltersFragment()
            true
        }
        cards_rv.adapter = adapter
    }

    private fun showFiltersFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )?.replace(R.id.container, FiltersFragment.newInstance())?.addToBackStack(null)?.commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        // TODO: Use the ViewModel


        viewModel.allResponse.observe(this, Observer {
            when(it.status){
                ReponseStatus.SUCCESS -> {
                    @Suppress("UNCHECKED_CAST")
                    if(it is ListApiResponse<*>){
                        main_fragment_progress.visibility = View.GONE
                        cards_rv.visibility = View.VISIBLE
                        adapter.replaceAll(it.items as List<Card>)
                        adapter.notifyDataSetChanged()
                    }
                }
                ReponseStatus.LOADING -> showLoading()
                ReponseStatus.ERROR -> showError()
            }

        })
        viewModel.getAll()
    }

    private fun showLoading() {
        main_fragment_progress.visibility = View.VISIBLE
        cards_rv.visibility = View.GONE
    }

    private fun showError() {
        main_fragment_progress.visibility = View.GONE
        cards_rv.visibility = View.VISIBLE
        Toast.makeText(context,"ERROR",Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
