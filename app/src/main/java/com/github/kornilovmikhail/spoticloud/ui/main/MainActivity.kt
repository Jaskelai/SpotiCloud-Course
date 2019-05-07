package com.github.kornilovmikhail.spoticloud.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView, CallbackFromFragments {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private val navigator = MySupportAppNavigator(this, R.id.main_container)

    private lateinit var bottomNav: BottomNavigationView

    @ProvidePresenter
    fun getPresenter(): MainPresenter = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .mainSubComponentBuilder()
            .withActivity(this)
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        mainPresenter.onCreate()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.detachNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onCleared()
    }

    override fun onBackPressed() {
        mainPresenter.onBackPressed()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showBottomBar() {
        enableBottomNavBar()
    }

    override fun enableBottomNavBar() {
        if (vs_bottom_nav != null) {
            bottomNav = vs_bottom_nav.inflate() as BottomNavigationView
            showTrackListChose()
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_action_search -> mainPresenter.onBottomSearchClicked()
                    R.id.bottom_action_tracks -> mainPresenter.onBottomTracksClicked()
                    R.id.bottom_action_trends -> mainPresenter.onBottomTrendsClicked()
                }
                true
            }
        }
    }

    override fun showToolbar() {
        enableToolbar()
    }

    override fun enableToolbar() {
        if (vs_toolbar != null) {
            val toolbarView = vs_toolbar.inflate()
            setSupportActionBar(toolbarView as Toolbar?)
        }
    }

    override fun showSearchChose() {
        bottomNav.selectedItemId = R.id.bottom_action_search
    }

    override fun showTrackListChose() {
        bottomNav.selectedItemId = R.id.bottom_action_tracks
    }

    override fun showTrendsChose() {
        bottomNav.selectedItemId = R.id.bottom_action_trends
    }
}
