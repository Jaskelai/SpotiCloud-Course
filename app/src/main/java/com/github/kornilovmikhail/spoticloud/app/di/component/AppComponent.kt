package com.github.kornilovmikhail.spoticloud.app.di.component

import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.app.di.module.ApplicationModule
import com.github.kornilovmikhail.spoticloud.app.di.module.NavigationModule
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.ui.MainActivity
import com.github.kornilovmikhail.spoticloud.ui.start.di.component.StartSubComponent
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)

    fun startSubComponentBuilder(): StartSubComponent.Builder
}
