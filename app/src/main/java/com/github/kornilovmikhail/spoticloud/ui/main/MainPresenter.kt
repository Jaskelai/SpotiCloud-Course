package com.github.kornilovmikhail.spoticloud.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import com.github.kornilovmikhail.spoticloud.navigation.FragmentBottomEnum
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.NavigatorHolder
import java.util.LinkedList
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router,
    private val navigatorHolder: NavigatorHolder,
    private val loginUseCase: LoginUseCase,
    private val queue: LinkedList<FragmentBottomEnum>
) : MvpPresenter<MainView>() {

    private val disposables = CompositeDisposable()

    fun onCreate() {
        disposables.add(loginUseCase.checkLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != "") {
                    router.startAtTrackScreen()
                    queue.add(FragmentBottomEnum.TRACKLIST)
                    showGeneralViews()
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

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun setNavigator(navigator: MySupportAppNavigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun detachNavigator() {
        navigatorHolder.removeNavigator()
    }

    fun onBottomSearchClicked() {
        router.navigateToSearch()
        queue.add(FragmentBottomEnum.SEARCH)
    }

    fun onBottomTracksClicked() {
        router.navigateToTrackList()
        queue.add(FragmentBottomEnum.TRACKLIST)
    }

    fun onBottomTrendsClicked() {
        router.navigateToTrends()
        queue.add(FragmentBottomEnum.TRENDS)
    }

    fun onBackPressed() {
        if (queue.size == 1) {
            router.exit()
        }
        if (queue.size > 1) {
            queue.removeLast()
            when (queue.last) {
                FragmentBottomEnum.SEARCH -> viewState.showSearchChose()
                FragmentBottomEnum.TRACKLIST -> viewState.showTrackListChose()
                FragmentBottomEnum.TRENDS -> viewState.showTrendsChose()
                else -> return
            }
        } else {
            router.back()
        }
    }

    fun showTrack() {
        viewState.showFooter()
    }

    fun onFooterClicked() {
        router.navigateToMusicPlayer()
    }

    private fun showGeneralViews() {
        viewState.showBottomBar()
        viewState.showToolbar()
    }
}
