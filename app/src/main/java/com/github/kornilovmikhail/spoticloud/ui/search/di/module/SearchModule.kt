package com.github.kornilovmikhail.spoticloud.ui.search.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import com.github.kornilovmikhail.spoticloud.ui.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @FeatureScope
    @Provides
    fun provideSearchPresenter(
        tracksUseCase: TracksUseCase
    ): SearchPresenter = SearchPresenter(tracksUseCase)
}
