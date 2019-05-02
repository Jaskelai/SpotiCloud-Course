package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.navigation.FragmentBottomEnum
import com.github.kornilovmikhail.spoticloud.navigation.LimitedUniqueQueue
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.navigation.router.RouterCiceroneImpl
import dagger.Module
import dagger.Provides
import java.util.LinkedList

@Module
class NavigationModule {

    @Provides
    @ApplicationScope
    fun provideRouter(router: RouterCiceroneImpl): Router = router

    @Provides
    @ApplicationScope
    fun provideLinkedSetWithGetLast(): LinkedList<FragmentBottomEnum> =
        LimitedUniqueQueue(FragmentBottomEnum.values().size)
}
