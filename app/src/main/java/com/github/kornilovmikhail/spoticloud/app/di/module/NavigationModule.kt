package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.navigation.router.RouterCiceroneImpl
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides
    @ApplicationScope
    fun provideRouter(router: RouterCiceroneImpl): Router = router
}
