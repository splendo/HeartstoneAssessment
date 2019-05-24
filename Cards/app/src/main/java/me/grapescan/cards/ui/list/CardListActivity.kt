package me.grapescan.cards.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ui.details.CardDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardListActivity : AppCompatActivity() {

    private val viewModel: CardListViewModel by viewModel()
    private val adapter = CardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)
        viewModel.cards.observe(this@CardListActivity, Observer<List<Card>> { adapter.submitList(it) })
        findViewById<RecyclerView>(R.id.card_list).run {
            this@run.adapter = this@CardListActivity.adapter.apply {
                onItemClick = { startActivity(CardDetailsActivity.createIntent(this@CardListActivity, it)) }
            }
            layoutManager = LinearLayoutManager(this@CardListActivity)
        }
    }
}



