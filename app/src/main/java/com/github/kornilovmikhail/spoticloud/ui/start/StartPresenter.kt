package com.github.kornilovmikhail.spoticloud.ui.start

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class StartPresenter(
    private val router: Router,
    private val spotifyUseCase: LoginSpotifyUseCase,
    private val soundcloudUseCase: LoginSoundcloudUseCase,
    private val loginUseCase: LoginUseCase
) : MvpPresenter<StartView>() {

    private val disposables = CompositeDisposable()

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
        loginUseCase.saveLogged()
        router.startAtTrackScreen()
        viewState.showBottomBar()
        viewState.showToolbar()
    }

    fun onSpotifyLoginResult(any: Any?) {
        val isSuccessful = spotifyUseCase.saveObject(any)
        if (isSuccessful) {
            viewState.disableSpotifyButton()
            viewState.showSnackBar()
        }
    }

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun checkSpotify() {
        disposables.add(
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
        )
    }

    private fun checkSoundCloud() {
        disposables.add(
            soundcloudUseCase.loadLocalSoundCloudToken()
                .subscribeOn(Schedulers.io())
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
        )
    }
}
