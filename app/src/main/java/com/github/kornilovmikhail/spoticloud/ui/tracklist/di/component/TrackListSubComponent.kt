package com.github.kornilovmikhail.spoticloud.ui.tracklist.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.tracklist.TrackListFragment
import com.github.kornilovmikhail.spoticloud.ui.tracklist.di.module.TrackListModule
import dagger.Subcomponent

@Subcomponent(
    modules = [TrackListModule::class]
)
@FeatureScope
interface TrackListSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): TrackListSubComponent
    }

    fun inject(startFragment: TrackListFragment)
}
