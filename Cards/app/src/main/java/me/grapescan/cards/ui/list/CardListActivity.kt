package me.grapescan.cards.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.inflate
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
                onItemClick = { viewModel.onCardClick(it) }
            }
            layoutManager = LinearLayoutManager(this@CardListActivity)
        }
    }
}



