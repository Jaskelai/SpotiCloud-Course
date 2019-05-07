package com.github.kornilovmikhail.spoticloud.ui.search.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.search.SearchFragment
import com.github.kornilovmikhail.spoticloud.ui.search.di.module.SearchModule
import dagger.Subcomponent

@Subcomponent(
    modules = [SearchModule::class]
)
@FeatureScope
interface SearchSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): SearchSubComponent
    }

    fun inject(searchFragment: SearchFragment)
}
