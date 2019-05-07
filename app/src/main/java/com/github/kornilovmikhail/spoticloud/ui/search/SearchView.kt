package com.github.kornilovmikhail.spoticloud.ui.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.kornilovmikhail.spoticloud.core.model.Track

@StateStrategyType(value = SkipStrategy::class)
interface SearchView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showSearchedTracks(tracks: List<Track>)

    fun showProgressBar()

    fun hideProgressBar()

    fun showErrorMessage()

    fun showNotFoundMessage()

    fun showTrackAddedMessage()
}
