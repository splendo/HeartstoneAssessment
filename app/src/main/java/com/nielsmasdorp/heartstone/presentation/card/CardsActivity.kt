package com.nielsmasdorp.heartstone.presentation.card

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.nielsmasdorp.heartstone.R
import com.nielsmasdorp.heartstone.presentation.card.grid.CardGridFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Main activity for the cards feature in the Heartstone app
 */
class CardsActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        if (savedInstanceState != null) {
            CURRENT_POSITION = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0)
            return
        }
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, CardGridFragment(), CardGridFragment::class.java.simpleName)
                .commit()
    }

    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_POSITION, CURRENT_POSITION)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    companion object {

        private const val KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION"

        /** Current clicked card position in the chosen set of cards */
        var CURRENT_POSITION = 0
    }
}