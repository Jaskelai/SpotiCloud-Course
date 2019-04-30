package com.github.kornilovmikhail.spoticloud.ui.tracklist.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import com.github.kornilovmikhail.spoticloud.ui.tracklist.TrackListPresenter
import dagger.Module
import dagger.Provides

@Module
class TrackListModule {

    @FeatureScope
    @Provides
    fun provideTrackListPresenter(
        tracksUseCase: TracksUseCase,
        loginSoundcloudUseCase: LoginSoundcloudUseCase
    ): TrackListPresenter = TrackListPresenter(tracksUseCase, loginSoundcloudUseCase)
}
