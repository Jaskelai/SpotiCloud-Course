package com.github.kornilovmikhail.spoticloud.ui.musicplayer.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.musicplayer.MusicPlayerPresenter
import dagger.Module
import dagger.Provides

@Module
class MusicPlayerModule {

    @FeatureScope
    @Provides
    fun provideMainPresenter(
        router: Router
    ): MusicPlayerPresenter = MusicPlayerPresenter(router)
}
