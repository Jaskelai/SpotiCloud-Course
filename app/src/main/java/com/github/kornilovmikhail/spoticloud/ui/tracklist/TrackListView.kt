package com.github.kornilovmikhail.spoticloud.ui.tracklist

import com.arellomobile.mvp.MvpView
import com.github.kornilovmikhail.spoticloud.core.model.Track

interface TrackListView : MvpView {

    fun showErrorMessage()

    fun showTracks(tracks: List<Track>)

    fun showProgressBar()

    fun hideProgressBar()
}
