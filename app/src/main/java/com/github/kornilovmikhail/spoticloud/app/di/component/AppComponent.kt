package com.github.kornilovmikhail.spoticloud.app.di.component

import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.app.di.module.*
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.data.di.RepositoryModule
import com.github.kornilovmikhail.spoticloud.data.di.SpotifyModule
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.di.component.LoginSoundcloudSubComponent
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity
import com.github.kornilovmikhail.spoticloud.ui.main.di.component.MainSubComponent
import com.github.kornilovmikhail.spoticloud.ui.start.di.component.StartSubComponent
import dagger.Component

@ApplicationScope
@Component(
    modules = [ApplicationModule::class, NavigationModule::class, CiceroneModule::class, SpotifyModule::class,
        SharedPrefModule::class, RepositoryModule::class, RetrofitModule::class]
)
interface AppComponent {

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)

    fun startSubComponentBuilder(): StartSubComponent.Builder
    fun mainSubComponentBuilder(): MainSubComponent.Builder
    fun loginSoundcloudSubComponentBuilder(): LoginSoundcloudSubComponent.Builder
}
