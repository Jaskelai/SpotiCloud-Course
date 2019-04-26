package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.BuildConfig
import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTokenResponseToToken
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import io.reactivex.Single

class UserRepositoryImpl(private val sharedPrefStorage: SharedPrefStorage, private val soundCloudApi: SoundCloudApi) :
    UserRepository {

    override fun saveSpotifyToken(token: String) {
        sharedPrefStorage.writeMessage(TOKEN_SPOTIFY, token)
    }

    override fun loadSouncloudToken(email: String, password: String): Single<TokenSoundCloud> =
        soundCloudApi.getToken(
            email,
            password,
            BuildConfig.SOUNDCLOUD_CLIENT_ID,
            BuildConfig.SOUNDCLOUD_CLIENT_SECRET,
            SOUNDCLOUD_GRANT_TYPE
        )
            .map { mapSoundCloudTokenResponseToToken(it) }

    override fun saveSoundCloudToken(token: String) {
        sharedPrefStorage.writeMessage(TOKEN_SOUNDCLOUD, token)
    }

    override fun loadLocalSoundcloudToken(): Single<String> = sharedPrefStorage.readMessage(TOKEN_SOUNDCLOUD)

    override fun loadLocalSpotifyToken(): Single<String> =
        sharedPrefStorage.readMessage(TOKEN_SPOTIFY)

    override fun saveLogged() {
        sharedPrefStorage.writeMessage(IS_LOGGED, IS_LOGGED)
    }

    override fun checkLogin(): Single<String> =
        sharedPrefStorage.readMessage(IS_LOGGED)

    companion object {
        private const val IS_LOGGED = "IS_LOGGED"
        private const val TOKEN_SPOTIFY = "TOKEN_SPOTIFY"
        private const val TOKEN_SOUNDCLOUD = "TOKEN_SOUNDCLOUD"
        private const val SOUNDCLOUD_GRANT_TYPE = "password"
    }
}
