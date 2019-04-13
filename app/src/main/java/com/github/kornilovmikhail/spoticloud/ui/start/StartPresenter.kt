package com.github.kornilovmikhail.spoticloud.ui.start

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router

@InjectViewState
class StartPresenter(private val router: Router, private val spotifyUseCase: LoginSpotifyUseCase) :
    MvpPresenter<StartView>() {

    fun onSoundcloudButtonClicked() {

    }

    fun onSpotifyButtonClicked(requestCode: Int) {
        router.navigateToLoginSpotifyScreen(requestCode)
    }

    fun onSnackBarClicked() {

    }

    fun onSpotifyLoginResult(any: Any?) {
        val isSuccessful = spotifyUseCase.saveObject(any)
        if (isSuccessful) {
            viewState.disableSpotifyButton()
            viewState.showSnackBar()
        }
    }
}
