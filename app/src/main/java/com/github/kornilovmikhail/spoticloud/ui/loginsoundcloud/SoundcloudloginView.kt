package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SoundcloudloginView : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun displayError()
}
