package com.github.kornilovmikhail.spoticloud.navigation.router

interface Router {

    fun navigateToStartScreen()

    fun navigateToLoginSoundcloudScreen()

    fun navigateToLoginSpotifyScreen(requestCode: Int)

    fun navigateToTrackList()

    fun navigateToSearch()

    fun navigateToTrends()

    fun startAtTrackScreen()

    fun back()

    fun exit()
}
