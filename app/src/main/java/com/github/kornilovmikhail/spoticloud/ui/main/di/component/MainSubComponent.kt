package com.github.kornilovmikhail.spoticloud.ui.main.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity
import com.github.kornilovmikhail.spoticloud.ui.main.di.module.MainModule
import dagger.Subcomponent

@Subcomponent(
    modules = [MainModule::class]
)
@FeatureScope
interface MainSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): MainSubComponent
    }

    fun inject(mainActivity: MainActivity)
}
