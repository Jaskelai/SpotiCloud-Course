package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SoundcloudLoginPresenter(private val router: Router, private val soundcloudUseCase: LoginSoundcloudUseCase) :
    MvpPresenter<SoundcloudloginView>() {

    private val disposables = CompositeDisposable()

    fun onBackArrowClicked() {
        router.back()
    }

    fun onSignInClicked(email: String, password: String) {
        disposables.add(soundcloudUseCase.signIn(email, password)
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
            ))
    }

    fun onCleared() {
        disposables.clear()
    }
}
