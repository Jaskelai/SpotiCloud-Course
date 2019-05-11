package com.github.kornilovmikhail.spoticloud.ui.trends

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.kornilovmikhail.spoticloud.core.model.Track

@StateStrategyType(value = SkipStrategy::class)
interface TrendsView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showTrendsTracks(tracks: List<Track>)

    fun showProgressBar()

    fun hideProgressBar()

    fun showErrorMessage()

    fun showTrackAddedMessage()

    fun sendTrackToPlayer(track: Track?)
}
