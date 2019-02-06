package laurens.hearthstoneassessment.activities.mainpage

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import laurens.hearthstoneassessment.R
import laurens.hearthstoneassessment.model.CardModel
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import laurens.hearthstoneassessment.application.HearthstoneAssessment
import laurens.hearthstoneassessment.activities.detailpage.DetailPageActivity
import laurens.hearthstoneassessment.activities.mainpage.grid.CardAdapter
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), MainPageContract.View {

    @Inject
    lateinit var presenter: MainPageContract.Presenter
    private val adapter = CardAdapter { presenter.onCardTap(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as HearthstoneAssessment).mainComponent.inject(this)
        setupGrid()
        presenter.initialize(this)
    }

    override fun goToDetailActivity() = startActivity<DetailPageActivity>()

    private fun setupGrid() {
        recycler_view.also {
            it.layoutManager = GridLayoutManager(this, 3)
            it.adapter = adapter
            it.addItemDecoration(
                DividerItemDecoration(
                    it.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
            it.addItemDecoration(
                DividerItemDecoration(
                    it.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun setCards(cards: LiveData<PagedList<CardModel>>) {
        cards.observe({ this.lifecycle }) {
            adapter.submitList(it)
        }
    }
}

