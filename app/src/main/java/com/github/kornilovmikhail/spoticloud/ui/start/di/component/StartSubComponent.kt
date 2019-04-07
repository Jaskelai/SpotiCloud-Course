package com.github.kornilovmikhail.spoticloud.ui.start.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.start.StartFragment
import com.github.kornilovmikhail.spoticloud.ui.start.di.module.StartModule
import dagger.Subcomponent

@Subcomponent(
    modules = [StartModule::class]
)
@FeatureScope
interface StartSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): StartSubComponent
    }

    fun inject(startFragment: StartFragment)
}
