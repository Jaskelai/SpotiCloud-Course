package com.github.kornilovmikhail.spoticloud.app.di.component

import android.content.Context
import com.github.kornilovmikhail.spoticloud.app.di.module.ApplicationModule
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun provideContext(): Context
}
