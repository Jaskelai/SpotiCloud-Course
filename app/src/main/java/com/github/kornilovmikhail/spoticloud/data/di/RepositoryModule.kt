package com.github.kornilovmikhail.spoticloud.data.di

import android.content.SharedPreferences
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import com.github.kornilovmikhail.spoticloud.data.repository.SharedPrefStorage
import com.github.kornilovmikhail.spoticloud.data.repository.UserRepositoryImpl
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideUserRepository(sharedPrefStorage: SharedPrefStorage, soundCloudApi: SoundCloudApi): UserRepository =
        UserRepositoryImpl(sharedPrefStorage, soundCloudApi)

    @Provides
    @ApplicationScope
    fun provideSharedPrefStorage(sharedPreferences: SharedPreferences): SharedPrefStorage =
        SharedPrefStorage(sharedPreferences)

    @Provides
    @ApplicationScope
    fun provideSoundcloudLoginUseCase(userRepository: UserRepository): LoginSoundcloudUseCase =
        LoginSoundcloudUseCase(userRepository)

    @Provides
    @ApplicationScope
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase = LoginUseCase(userRepository)

    @Provides
    @ApplicationScope
    fun provideSpotifyLoginUseCase(userRepository: UserRepository): LoginSpotifyUseCase =
        LoginSpotifyUseCase(userRepository)
}
