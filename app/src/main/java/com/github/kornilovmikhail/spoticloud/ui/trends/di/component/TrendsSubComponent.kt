package com.github.kornilovmikhail.spoticloud.ui.trends.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.trends.TrendsFragment
import com.github.kornilovmikhail.spoticloud.ui.trends.di.module.TrendsModule
import dagger.Subcomponent

@Subcomponent(
    modules = [TrendsModule::class]
)
@FeatureScope
interface TrendsSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): TrendsSubComponent
    }

    fun inject(trendsFragment: TrendsFragment)
}
