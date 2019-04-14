package com.github.kornilovmikhail.spoticloud.navigation.router

interface Router {

    fun navigateToStartScreen()

    fun navigateToLoginSoundcloudScreen()

    fun navigateToLoginSpotifyScreen(requestCode: Int)

    fun back()
}
