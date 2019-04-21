package com.github.kornilovmikhail.spoticloud.data.repository

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
        soundCloudApi.getToken(email, password, SOUNDCLOUD_CLIENT_ID, SOUNDCLOUD_CLIENT_SECRET, SOUNDCLOUD_GRANT_TYPE)
            .map { mapSoundCloudTokenResponseToToken(it) }

    override fun saveSoundCloudToken(token: String) {
        sharedPrefStorage.writeMessage(TOKEN_SOUNDCLOUD, token)
    }

    override fun loadLocalSoundcloudToken(): Single<String> = sharedPrefStorage.readMessage(TOKEN_SOUNDCLOUD)

    override fun loadLocalSpotifyToken(): Single<String> =
        sharedPrefStorage.readMessage(TOKEN_SPOTIFY)

    companion object {
        private const val TOKEN_SPOTIFY = "TOKEN_SPOTIFY"
        private const val TOKEN_SOUNDCLOUD = "TOKEN_SOUNDCLOUD"
        private const val SOUNDCLOUD_GRANT_TYPE = "password"
        private const val SOUNDCLOUD_CLIENT_ID = "51762b5b2ec86582ea95a9d816077654"
        private const val SOUNDCLOUD_CLIENT_SECRET = "9923d03b898d96820129c1b6720d915c"
    }
}
