package me.grapescan.cards.ui.list

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_card_list.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ui.preview.CardPreviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardListActivity : AppCompatActivity() {

    private val viewModel: CardListViewModel by viewModel()
    private val adapter = CardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        cardList.run {
            ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
                this.setPadding(paddingLeft, insets.systemWindowInsetTop, paddingRight, insets.systemWindowInsetBottom)
                insets.replaceSystemWindowInsets(insets.systemWindowInsetLeft, 0, insets.systemWindowInsetRight, 0)
            }
            this@run.adapter = this@CardListActivity.adapter.apply {
                onItemClick = { view, item ->
                    val intent = CardPreviewActivity.createIntent(this@CardListActivity, item)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        // TODO: improve reverse transition to collapse into proper grid item
                        // https://android-developers.googleblog.com/2018/02/continuous-shared-element-transitions.html
                        startActivity(
                            intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this@CardListActivity,
                                view,
                                resources.getString(R.string.transition_card)
                            ).toBundle()
                        )
                    } else {
                        startActivity(intent)
                    }

                }
            }
            layoutManager = GridLayoutManager(this@CardListActivity, 3)
        }
        viewModel.cards.observe(this@CardListActivity, Observer<List<Card>> { adapter.submitList(it) })
    }
}



