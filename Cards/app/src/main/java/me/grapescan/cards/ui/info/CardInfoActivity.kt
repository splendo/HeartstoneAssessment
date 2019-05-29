package me.grapescan.cards.ui.info

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_card_info.*
import kotlinx.android.synthetic.main.partial_info_item.view.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.ParcelableExtra

class CardInfoActivity : AppCompatActivity() {

    companion object {

        private var Intent.card by ParcelableExtra<Card>()

        fun createIntent(context: Context, card: Card) =
            Intent(context, CardInfoActivity::class.java).apply {
                this.card = card
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        ViewCompat.setOnApplyWindowInsetsListener(content) { view, insets ->
            toolbar.setPadding(
                toolbar.paddingLeft,
                insets.systemWindowInsetTop,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )
            content.setPadding(
                content.paddingLeft,
                content.paddingTop,
                content.paddingRight,
                insets.systemWindowInsetBottom
            )
            insets.replaceSystemWindowInsets(insets.systemWindowInsetLeft, 0, insets.systemWindowInsetRight, 0)
        }
        toolbar.run {
            title = intent.card.name
            navigationIcon = ContextCompat.getDrawable(this@CardInfoActivity, R.drawable.ic_close_black_24dp)
            setNavigationOnClickListener { finish() }
        }
        with(intent.card) {
            showInfo(
                mapOf<String, CharSequence>(
                    getString(R.string.label_text) to text,
                    getString(R.string.label_card_set) to cardSet,
                    getString(R.string.label_type) to type,
                    getString(R.string.label_rarity) to rarity,
                    getString(R.string.label_cost) to cost.toString(),
                    getString(R.string.label_attack) to attack.toString(),
                    getString(R.string.label_health) to health.toString(),
                    getString(R.string.label_flavor) to flavor,
                    getString(R.string.label_artist) to artist,
                    getString(R.string.label_collectible) to if (collectible) getString(R.string.yes) else getString(R.string.no),
                    getString(R.string.label_player_class) to playerClass,
                    getString(R.string.label_how_to_get_gold) to howToGetGold,
                    getString(R.string.label_mechanics) to mechanics.joinToString(", ")
                )
            )
        }
    }

    private fun showInfo(info: Map<String, CharSequence>) = info.forEach { (param, value) ->
        content.addView(LayoutInflater.from(this)
            .inflate(R.layout.partial_info_item, content, false).apply {
                paramName.text = param
                paramValue.text = value
            })
    }
}
