package com.github.kornilovmikhail.spoticloud.navigation.router

import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.ForwardWithResult
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.SpotifyLoginScreen
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens.StartScreen
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Forward
import javax.inject.Inject

class RouterCiceroneImpl @Inject constructor(
    private val startScreen: StartScreen,
    private val spotifyLoginScreen: SpotifyLoginScreen
) : Router, BaseRouter() {

    override fun navigateToStartScreen() {
        executeCommands(Forward(startScreen))
    }

    override fun navigateToLoginSpotifyScreen(requestCode: Int) {
        executeCommands(ForwardWithResult(spotifyLoginScreen, requestCode))
    }
}
