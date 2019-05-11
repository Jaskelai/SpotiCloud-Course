package com.github.kornilovmikhail.spoticloud.ui.trends.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import com.github.kornilovmikhail.spoticloud.ui.trends.TrendsPresenter
import dagger.Module
import dagger.Provides

@Module
class TrendsModule {

    @FeatureScope
    @Provides
    fun provideTrendsPresenter(
        tracksUseCase: TracksUseCase
    ): TrendsPresenter = TrendsPresenter(tracksUseCase)
}
