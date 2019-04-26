package com.github.kornilovmikhail.spoticloud.core.interfaces

import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import io.reactivex.Single

interface UserRepository {

    fun saveSpotifyToken(token: String)

    fun loadSouncloudToken(email: String, password: String): Single<TokenSoundCloud>

    fun saveSoundCloudToken(token: String)

    fun loadLocalSpotifyToken(): Single<String>

    fun loadLocalSoundcloudToken(): Single<String>

    fun saveLogged()

    fun checkLogin(): Single<String>
}
