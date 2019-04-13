package com.github.kornilovmikhail.spoticloud.ui.main.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @FeatureScope
    @Provides
    fun provideMainPresenter(router: Router): MainPresenter = MainPresenter(router)
}
