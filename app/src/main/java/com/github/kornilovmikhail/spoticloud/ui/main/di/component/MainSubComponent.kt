package com.github.kornilovmikhail.spoticloud.ui.main.di.component

import androidx.fragment.app.FragmentActivity
import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity
import com.github.kornilovmikhail.spoticloud.ui.main.di.module.MainModule
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [MainModule::class]
)
@FeatureScope
interface MainSubComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun withActivity(activity: FragmentActivity): Builder

        fun build(): MainSubComponent
    }

    fun inject(mainActivity: MainActivity)
}
