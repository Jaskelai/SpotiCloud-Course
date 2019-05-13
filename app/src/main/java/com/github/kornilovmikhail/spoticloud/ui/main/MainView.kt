package com.github.kornilovmikhail.spoticloud.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun showErrorMessage()

    @StateStrategyType(value = SkipStrategy::class)
    fun showBottomBar()

    @StateStrategyType(value = SkipStrategy::class)
    fun showToolbar()

    fun showSearchChose()

    fun showTrackListChose()

    fun showTrendsChose()

    @StateStrategyType(value = SkipStrategy::class)
    fun showFooter()

    fun sendPlayerStartedToService()
}
