package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.SoundcloudLoginScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.SpotifyLoginScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.StartScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.TrackListScreen
import com.github.kornilovmikhail.spoticloud.navigation.router.RouterCiceroneImpl
import com.spotify.sdk.android.authentication.AuthenticationRequest
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder

@Module
class CiceroneModule {

    @Provides
    @ApplicationScope
    fun provideCiceroneRouter(
        startScreen: StartScreen,
        spotifyLoginScreen: SpotifyLoginScreen,
        soundcloudLoginScreen: SoundcloudLoginScreen,
        trackListScreen: TrackListScreen
    ): RouterCiceroneImpl =
        RouterCiceroneImpl(startScreen, spotifyLoginScreen, soundcloudLoginScreen, trackListScreen)

    @Provides
    @ApplicationScope
    fun provideCicerone(ciceroneRouter: RouterCiceroneImpl): Cicerone<RouterCiceroneImpl> =
        Cicerone.create(ciceroneRouter)

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(cicerone: Cicerone<RouterCiceroneImpl>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @ApplicationScope
    fun provideStartScreen(): StartScreen = StartScreen()

    @Provides
    @ApplicationScope
    fun provideSpotifyLoginScreen(authenticationRequest: AuthenticationRequest): SpotifyLoginScreen =
        SpotifyLoginScreen(authenticationRequest)

    @Provides
    @ApplicationScope
    fun provideSoundcloudLoginScreen(): SoundcloudLoginScreen = SoundcloudLoginScreen()

    @Provides
    @ApplicationScope
    fun provideTrackListScreen(): TrackListScreen = TrackListScreen()
}
