package me.grapescan.cards.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.ParcelableExtra
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CardDetailsActivity : AppCompatActivity() {

    companion object {

        private var Intent.card by ParcelableExtra<Card>()

        fun createIntent(context: Context, card: Card) = Intent(context, CardDetailsActivity::class.java).apply {
            this.card = card
        }
    }

    private val viewModel: CardDetailsViewModel by inject(parameters = { parametersOf(intent.card) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        findViewById<TextView>(R.id.title).text = viewModel.card.name
    }
}
