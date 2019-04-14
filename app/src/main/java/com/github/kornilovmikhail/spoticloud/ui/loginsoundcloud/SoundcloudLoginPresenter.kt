package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router

@InjectViewState
class SoundcloudLoginPresenter(private val router: Router, private val soundcloudUseCase: LoginSoundcloudUseCase) :
    MvpPresenter<SoundcloudloginView>() {

    fun onBackArrowClicked() {
        router.back()
    }

    fun onSignInClicked(email: String, password: String) {
        soundcloudUseCase.signIn(email, password)
    }
}
