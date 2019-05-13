package com.github.kornilovmikhail.spoticloud.ui.musicplayer

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SkipStrategy::class)
interface MusicPlayerView : MvpView {
}
