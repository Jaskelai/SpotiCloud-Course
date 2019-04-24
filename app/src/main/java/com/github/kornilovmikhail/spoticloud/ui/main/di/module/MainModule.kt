package com.github.kornilovmikhail.spoticloud.ui.main.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.NavigatorHolder

@Module
class MainModule {

    @FeatureScope
    @Provides
    fun provideMainPresenter(
        router: Router,
        navigatorHolder: NavigatorHolder
    ): MainPresenter = MainPresenter(router, navigatorHolder)


}
