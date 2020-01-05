package com.krayem.hearthstone.ui.main.splash

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.krayem.hearthstone.MainActivity

import com.krayem.hearthstone.R
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.isDbEmptyResponse.observe(this, Observer {
            if(!it){
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }else{
                splash_fragment_label.text = getString(R.string.populating_db)
            }
        })
        viewModel.checkDatabase()
    }

}
