package com.github.kornilovmikhail.spoticloud.navigation.router

interface Router {

    fun navigateToStartScreen()

    fun navigateToLoginSpotifyScreen(requestCode: Int)
}
