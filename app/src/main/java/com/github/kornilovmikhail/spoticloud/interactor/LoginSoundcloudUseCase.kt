package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import io.reactivex.Single

class LoginSoundcloudUseCase(private val userRepository: UserRepository) {

    fun signIn(email: String, password: String): Single<String> = userRepository.loadSouncloudToken(email, password)
}
