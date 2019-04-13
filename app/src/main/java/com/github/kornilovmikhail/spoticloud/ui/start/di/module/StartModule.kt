package com.github.kornilovmikhail.spoticloud.ui.start.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.start.StartPresenter
import dagger.Module
import dagger.Provides

@Module
class StartModule {

    @FeatureScope
    @Provides
    fun provideStartPresenter(router: Router, spotifyUseCase: LoginSpotifyUseCase): StartPresenter =
        StartPresenter(router, spotifyUseCase)

    @FeatureScope
    @Provides
    fun provideSpotifyLoginUseCase(userRepository: UserRepository): LoginSpotifyUseCase =
        LoginSpotifyUseCase(userRepository)
}
