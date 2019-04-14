package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.SoundcloudLoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginSoundcloudModule {

    @FeatureScope
    @Provides
    fun provideSoundcloudLoginPresenter(
        router: Router,
        soundlcloudUseCase: LoginSoundcloudUseCase
    ): SoundcloudLoginPresenter =
        SoundcloudLoginPresenter(router, soundlcloudUseCase)

    @FeatureScope
    @Provides
    fun provideSoundcloudLoginUseCase(userRepository: UserRepository): LoginSoundcloudUseCase =
        LoginSoundcloudUseCase(userRepository)
}
