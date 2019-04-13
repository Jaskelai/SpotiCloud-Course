package com.github.kornilovmikhail.spoticloud.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {
    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @ProvidePresenter
    fun getPresenter(): MainPresenter = mainPresenter

    private val navigator =
        MySupportAppNavigator(this, R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .mainSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}