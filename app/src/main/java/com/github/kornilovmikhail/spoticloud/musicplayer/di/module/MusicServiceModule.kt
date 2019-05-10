package com.github.kornilovmikhail.spoticloud.musicplayer.di.module


import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.musicplayer.MusicService
import dagger.Module
import dagger.Provides

@Module
class MusicServiceModule {

    @Provides
    @FeatureScope
    fun providesBinder(): MusicService.OnBinder = MusicService().OnBinder()
}
