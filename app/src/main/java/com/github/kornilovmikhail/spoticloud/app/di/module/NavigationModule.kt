package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class NavigationModule {

    @Provides
    @ApplicationScope
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @ApplicationScope
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder
}
