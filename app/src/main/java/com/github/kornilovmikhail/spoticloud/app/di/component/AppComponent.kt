package com.github.kornilovmikhail.spoticloud.app.di.component

import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.app.di.module.*
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.data.di.RepositoryModule
import com.github.kornilovmikhail.spoticloud.data.di.SpotifySDKModule
import com.github.kornilovmikhail.spoticloud.musicplayerservice.MusicService
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.di.component.LoginSoundcloudSubComponent
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity
import com.github.kornilovmikhail.spoticloud.ui.main.di.component.MainSubComponent
import com.github.kornilovmikhail.spoticloud.ui.search.di.component.SearchSubComponent
import com.github.kornilovmikhail.spoticloud.ui.start.di.component.StartSubComponent
import com.github.kornilovmikhail.spoticloud.ui.tracklist.di.component.TrackListSubComponent
import com.github.kornilovmikhail.spoticloud.ui.trends.di.component.TrendsSubComponent
import com.squareup.picasso.Picasso
import dagger.Component

@ApplicationScope
@Component(
    modules = [ApplicationModule::class, NavigationModule::class, CiceroneModule::class, SpotifySDKModule::class,
        SharedPrefModule::class, RepositoryModule::class, MusicModule::class,
        RetrofitModule::class, RoomModule::class, PicassoModule::class]
)
interface AppComponent {

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(musicService: MusicService)

    fun startSubComponentBuilder(): StartSubComponent.Builder
    fun mainSubComponentBuilder(): MainSubComponent.Builder
    fun loginSoundcloudSubComponentBuilder(): LoginSoundcloudSubComponent.Builder
    fun trackListSubComponentBuilder(): TrackListSubComponent.Builder
    fun searchSubComponentBuilder(): SearchSubComponent.Builder
    fun trendsSubComponentBuilder(): TrendsSubComponent.Builder

    fun providePicasso(): Picasso
}
