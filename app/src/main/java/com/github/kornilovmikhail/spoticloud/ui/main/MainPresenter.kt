package com.github.kornilovmikhail.spoticloud.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router,
    private val navigatorHolder: NavigatorHolder,
    private val loginUseCase: LoginUseCase
) : MvpPresenter<MainView>() {

    private val disposables = CompositeDisposable()

    fun onStart() {
        disposables.add(loginUseCase.checkLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != "") {
                        router.navigateToTrackList()
                        viewState.showBottomBar()
                    } else {
                        router.navigateToStartScreen()
                    }
                },
                {
                    viewState.showErrorMessage()
                }
            )
        )
    }

    fun setNavigator(navigator: MySupportAppNavigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun detachNavigator() {
        navigatorHolder.removeNavigator()
    }

}
