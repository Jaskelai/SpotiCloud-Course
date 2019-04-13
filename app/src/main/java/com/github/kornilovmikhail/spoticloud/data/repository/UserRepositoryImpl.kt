package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository

class UserRepositoryImpl(private val sharedPrefStorage: SharedPrefStorage) : UserRepository {

    override fun saveSpotifyToken(token: String) {
        sharedPrefStorage.writeMessage(TOKEN_SPOTIFY, token)
    }

    companion object {
        private const val TOKEN_SPOTIFY = "TOKEN_SPOTIFY"
    }
}
