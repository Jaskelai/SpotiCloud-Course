package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.musicplayerservice.MusicServiceConnection
import com.github.kornilovmikhail.spoticloud.musicplayerservice.MusicServiceHelper
import dagger.Module
import dagger.Provides

@Module
class MusicModule {

    @ApplicationScope
    @Provides
    fun provideServiceHelper(): MusicServiceHelper = MusicServiceHelper()

    @ApplicationScope
    @Provides
    fun provideMusicServiceConnection(): MusicServiceConnection = MusicServiceConnection()
}