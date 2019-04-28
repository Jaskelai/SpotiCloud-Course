package com.github.kornilovmikhail.spoticloud.navigation.router

import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.Exit
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.ForwardWithResult
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.SingleFragmentForwardCommand
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.*
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class RouterCiceroneImpl @Inject constructor(
    private val startScreen: StartScreen,
    private val spotifyLoginScreen: SpotifyLoginScreen,
    private val soundcloudLoginScreen: SoundcloudLoginScreen,
    private val trackListScreen: TrackListScreen,
    private val searchScreen: SearchScreen,
    private val trendsScreen: TrendsScreen
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
        executeCommands(SingleFragmentForwardCommand(trackListScreen))
    }

    override fun navigateToSearch() {
        executeCommands(SingleFragmentForwardCommand(searchScreen))
    }

    override fun navigateToTrends() {
        executeCommands(SingleFragmentForwardCommand(trendsScreen))
    }

    override fun startAtTrackScreen() {
        executeCommands(Replace(trackListScreen))
    }

    override fun back() {
        executeCommands(Back())
    }

    override fun exit() {
        executeCommands(Exit())
    }
}
