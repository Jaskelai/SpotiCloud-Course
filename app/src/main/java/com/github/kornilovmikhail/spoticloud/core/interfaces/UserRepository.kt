package com.github.kornilovmikhail.spoticloud.core.interfaces

interface UserRepository {

    fun saveSpotifyToken(token: String)
}
