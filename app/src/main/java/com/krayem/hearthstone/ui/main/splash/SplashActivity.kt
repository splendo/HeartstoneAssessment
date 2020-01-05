package com.krayem.hearthstone.ui.main.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krayem.hearthstone.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportFragmentManager.beginTransaction().replace(R.id.splash_container,SplashFragment.newInstance()).commit()
    }

    override fun onBackPressed() {
        // nothing
    }
}
