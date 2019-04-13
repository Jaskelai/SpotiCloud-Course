package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.spotify.sdk.android.authentication.AuthenticationResponse

class LoginSpotifyUseCase(private val userRepository: UserRepository) {

    fun saveObject(any: Any?): Boolean {
        val token = (any as AuthenticationResponse).accessToken
        if (token != null) {
            userRepository.saveSpotifyToken(token)
            return true
        }
        return false
    }
}
