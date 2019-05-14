package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LoginSoundcloudUseCase(private val userRepository: UserRepository) {

    fun signIn(email: String, password: String): Single<TokenSoundCloud> =
        userRepository.loadSouncloudToken(email, password)
            .subscribeOn(Schedulers.io())

    fun saveToken(token: String) {
        userRepository.saveSoundCloudToken(token)
    }

    fun saveRefreshToken(token: String) {
        userRepository.saveSoundCloudRefreshToken(token)
    }

    fun loadLocalSoundCloudToken(): Single<String> = userRepository.loadLocalSoundcloudToken()
        .subscribeOn(Schedulers.io())

    fun loadSoundCloudTokenByRefreshToken(): Single<String> = userRepository.loadLocalSoundCloudRefreshToken()
        .observeOn(Schedulers.io())
        .flatMap {
            userRepository.loadSoundCloudTokenByRefreshToken(it)
        }
        .map {
            userRepository.saveSoundCloudToken(it.accessToken)
            userRepository.saveSoundCloudRefreshToken(it.refreshToken)
            it.accessToken
        }
}
