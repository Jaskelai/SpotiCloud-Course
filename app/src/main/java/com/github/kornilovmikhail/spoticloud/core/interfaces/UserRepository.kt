package com.github.kornilovmikhail.spoticloud.core.interfaces

import io.reactivex.Single

interface UserRepository {

    fun saveSpotifyToken(token: String)

    fun loadSouncloudToken(email: String, password: String): Single<String>
}
