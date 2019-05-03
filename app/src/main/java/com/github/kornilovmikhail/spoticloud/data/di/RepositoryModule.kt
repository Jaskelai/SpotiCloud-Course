package com.github.kornilovmikhail.spoticloud.data.di

import android.content.SharedPreferences
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackDAO
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import com.github.kornilovmikhail.spoticloud.data.network.api.SpotifyApi
import com.github.kornilovmikhail.spoticloud.data.repository.SharedPrefStorage
import com.github.kornilovmikhail.spoticloud.data.repository.TrackRepositorySoundcloudImpl
import com.github.kornilovmikhail.spoticloud.data.repository.TrackRepositorySpotifyImpl
import com.github.kornilovmikhail.spoticloud.data.repository.UserRepositoryImpl
import com.github.kornilovmikhail.spoticloud.interactor.*
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideUserRepository(sharedPrefStorage: SharedPrefStorage, soundCloudApi: SoundCloudApi): UserRepository =
        UserRepositoryImpl(sharedPrefStorage, soundCloudApi)

    @Provides
    @ApplicationScope
    @Named(SOUNDCLOUD_TRACK_REPOSITORY)
    fun provideSoundcloudRepository(
        soundCloudApi: SoundCloudApi,
        trackDao: TrackDAO
    ): TrackRepository =
        TrackRepositorySoundcloudImpl(soundCloudApi, trackDao)

    @Provides
    @ApplicationScope
    @Named(SPOTIFY_TRACK_REPOSITORY)
    fun provideSpotifyRepository(spotifyApi: SpotifyApi, trackDao: TrackDAO): TrackRepository =
        TrackRepositorySpotifyImpl(spotifyApi, trackDao)

    @Provides
    @ApplicationScope
    fun provideSharedPrefStorage(sharedPreferences: SharedPreferences): SharedPrefStorage =
        SharedPrefStorage(sharedPreferences)

    @Provides
    @ApplicationScope
    fun provideTracksUseCase(
        tracksSoundcloudUseCase: TracksSoundcloudUseCase,
        tracksSpotifyUseCase: TracksSpotifyUseCase
    ): TracksUseCase = TracksUseCase(tracksSoundcloudUseCase, tracksSpotifyUseCase)

    @Provides
    @ApplicationScope
    fun provideTracksSoundcloudUseCase(
        loginSoundcloudUseCase: LoginSoundcloudUseCase,
        @Named(SOUNDCLOUD_TRACK_REPOSITORY) trackSoundcloudRepository: TrackRepository
    ): TracksSoundcloudUseCase =
        TracksSoundcloudUseCase(loginSoundcloudUseCase, trackSoundcloudRepository)

    @Provides
    @ApplicationScope
    fun provideTracksSpotifyUseCase(
        loginSpotifyUseCase: LoginSpotifyUseCase,
        @Named(SPOTIFY_TRACK_REPOSITORY) trackSpotifyRepository: TrackRepository
    ): TracksSpotifyUseCase =
        TracksSpotifyUseCase(loginSpotifyUseCase, trackSpotifyRepository)

    @Provides
    @ApplicationScope
    fun provideLoginSoundloudUseCase(userRepository: UserRepository): LoginSoundcloudUseCase =
        LoginSoundcloudUseCase(userRepository)

    @Provides
    @ApplicationScope
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase = LoginUseCase(userRepository)

    @Provides
    @ApplicationScope
    fun provideSpotifyLoginUseCase(userRepository: UserRepository): LoginSpotifyUseCase =
        LoginSpotifyUseCase(userRepository)

    companion object {
        private const val SOUNDCLOUD_TRACK_REPOSITORY = "SOUNDCLOUD_TRACK_REPOSITORY"
        private const val SPOTIFY_TRACK_REPOSITORY = "SPOTIFY_TRACK_REPOSITORY"
    }
}
