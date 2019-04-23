package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class SoundcloudLoginPresenter(private val router: Router, private val soundcloudUseCase: LoginSoundcloudUseCase) :
    MvpPresenter<SoundcloudloginView>() {

    fun onBackArrowClicked() {
        router.back()
    }

    fun onSignInClicked(email: String, password: String) {
        soundcloudUseCase.signIn(email, password)
            .doOnSubscribe {
                viewState.showProgressBar()
            }
            .doAfterTerminate {
                viewState.hideProgressBar()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    soundcloudUseCase.saveToken(it.accessToken)
                    router.back()
                },
                {
                    viewState.displayError()
                }
            )
    }
}
