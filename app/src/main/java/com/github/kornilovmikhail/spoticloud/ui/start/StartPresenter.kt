package com.github.kornilovmikhail.spoticloud.ui.start

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class StartPresenter(
    private val router: Router,
    private val spotifyUseCase: LoginSpotifyUseCase,
    private val soundcloudUseCase: LoginSoundcloudUseCase
) : MvpPresenter<StartView>() {

    fun onResume() {
        checkSpotify()
        checkSoundCloud()
    }

    fun onSoundcloudButtonClicked() {
        router.navigateToLoginSoundcloudScreen()
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

    private fun checkSpotify() {
        spotifyUseCase.loadLocalSpotifyToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != "") {
                        viewState.disableSpotifyButton()
                        viewState.showSnackBar()
                    }
                },
                {
                    viewState.showErrorMessage()
                }
            )
    }

    private fun checkSoundCloud() {
        soundcloudUseCase.loadLocalSoundCloudToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != "") {
                        viewState.disableSoundCloudButton()
                        viewState.showSnackBar()
                    }
                },
                {
                    viewState.showErrorMessage()
                }
            )
    }
}
