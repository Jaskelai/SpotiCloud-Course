package com.github.kornilovmikhail.spoticloud.ui.musicplayer

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class MusicPlayerPresenter(private val router: Router) : MvpPresenter<MusicPlayerView>() {
    private val disposables = CompositeDisposable()

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun onBackButtonClicked() {
        router.back()
    }
}
