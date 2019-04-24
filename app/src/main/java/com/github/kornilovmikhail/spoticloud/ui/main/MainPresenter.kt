package com.github.kornilovmikhail.spoticloud.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router,
    private val navigatorHolder: NavigatorHolder
) : MvpPresenter<MainView>() {

    fun onStart() {
        router.navigateToStartScreen()
    }

    fun setNavigator(navigator: MySupportAppNavigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun detachNavigator() {
        navigatorHolder.removeNavigator()
    }

}
