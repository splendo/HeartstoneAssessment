package com.krayem.hearthstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krayem.hearthstone.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_root, MainFragment.newInstance())
                .commitNow()
        }
    }

}
