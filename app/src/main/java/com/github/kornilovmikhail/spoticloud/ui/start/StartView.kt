package com.github.kornilovmikhail.spoticloud.ui.start

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface StartView : MvpView {

    fun disableSpotifyButton()
    fun showSnackBar()
}
