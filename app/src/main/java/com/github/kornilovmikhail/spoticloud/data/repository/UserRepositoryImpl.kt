package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(private val sharedPrefStorage: SharedPrefStorage) : UserRepository {

    override fun saveSpotifyToken(token: String) {
        sharedPrefStorage.writeMessage(TOKEN_SPOTIFY, token)
    }

    override fun loadSouncloudToken(email: String, password: String): Single<String> {
        return Single.just(null)
        //TODO
    }

    companion object {
        private const val TOKEN_SPOTIFY = "TOKEN_SPOTIFY"
    }
}
