package com.github.kornilovmikhail.spoticloud.app.di.module

import android.app.Application
import android.content.Context
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {
    @Provides
    @ApplicationScope
    fun provideContext(): Context = app.applicationContext
}
