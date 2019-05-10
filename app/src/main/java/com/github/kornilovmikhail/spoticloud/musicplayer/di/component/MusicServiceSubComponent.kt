package com.github.kornilovmikhail.spoticloud.musicplayer.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.musicplayer.MusicService
import com.github.kornilovmikhail.spoticloud.musicplayer.di.module.MusicServiceModule
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [MusicServiceModule::class]
)
@FeatureScope
interface MusicServiceSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): MusicServiceSubComponent
    }

    fun inject(musicService: MusicService)
}
