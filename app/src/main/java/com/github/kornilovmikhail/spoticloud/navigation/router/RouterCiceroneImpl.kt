package com.github.kornilovmikhail.spoticloud.navigation.router

import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.ForwardWithResult
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.SoundcloudLoginScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.SpotifyLoginScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.StartScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.TrackListScreen
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class RouterCiceroneImpl @Inject constructor(
    private val startScreen: StartScreen,
    private val spotifyLoginScreen: SpotifyLoginScreen,
    private val soundcloudLoginScreen: SoundcloudLoginScreen,
    private val trackListScreen: TrackListScreen
) : Router, BaseRouter() {

    override fun navigateToStartScreen() {
        executeCommands(Replace(startScreen))
    }

    override fun navigateToLoginSoundcloudScreen() {
        executeCommands(Forward(soundcloudLoginScreen))
    }

    override fun navigateToLoginSpotifyScreen(requestCode: Int) {
        executeCommands(ForwardWithResult(spotifyLoginScreen, requestCode))
    }

    override fun navigateToTrackList() {
        executeCommands(Replace(trackListScreen))
    }

    override fun back() {
        executeCommands(Back())
    }
}
