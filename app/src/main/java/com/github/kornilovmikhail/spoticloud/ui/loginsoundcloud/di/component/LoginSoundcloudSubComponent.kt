package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.di.component

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.SoundcloudLoginFragment
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.di.module.LoginSoundcloudModule
import dagger.Subcomponent

@Subcomponent(
    modules = [LoginSoundcloudModule::class]
)
@FeatureScope
interface LoginSoundcloudSubComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): LoginSoundcloudSubComponent
    }

    fun inject(loginSoundcloudLoginFragment: SoundcloudLoginFragment)
}
