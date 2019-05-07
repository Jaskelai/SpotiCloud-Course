package com.github.kornilovmikhail.spoticloud.ui.tracklist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.kornilovmikhail.spoticloud.core.model.Track

@StateStrategyType(value = SkipStrategy::class)
interface TrackListView : MvpView {

    fun showErrorMessage()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showTracks(tracks: List<Track>)

    fun showProgressBar()

    fun hideProgressBar()

    fun showEmptyTracksMessage()
}
