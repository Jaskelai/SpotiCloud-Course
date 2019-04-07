package com.github.kornilovmikhail.spoticloud.ui.start.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.start.StartPresenter
import dagger.Module
import dagger.Provides

@Module
class StartModule {
    @FeatureScope
    @Provides
    internal fun provideStartPresenter(): StartPresenter = StartPresenter()
}
