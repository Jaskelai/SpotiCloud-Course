package com.github.kornilovmikhail.spoticloud.ui.main

import com.github.kornilovmikhail.spoticloud.core.model.Track

interface CallbackFromFragments {

    fun enableBottomNavBar()

    fun enableToolbar()

    fun sendTrackToPlayer(track: Track?)

    fun hideMainViews()

    fun showMainViews()

    fun nextTrack()

    fun prevTrack()

    fun pauseTrack()

    fun resumeTrack()
}
