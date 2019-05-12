package com.github.kornilovmikhail.spoticloud.ui.musicplayer.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.musicplayer.MusicPlayerFragment
import com.github.kornilovmikhail.spoticloud.ui.musicplayer.di.module.MusicPlayerModule
import dagger.Subcomponent

@Subcomponent(
    modules = [MusicPlayerModule::class]
)
@FeatureScope
interface MusicPlayerSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): MusicPlayerSubComponent
    }

    fun inject(musicPlayerFragment: MusicPlayerFragment)
}
