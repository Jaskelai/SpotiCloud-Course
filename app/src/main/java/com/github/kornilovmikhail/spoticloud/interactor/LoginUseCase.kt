package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.UserRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LoginUseCase(private val userRepository: UserRepository) {

    fun checkLogin(): Single<String> = userRepository.checkLogin()
        .subscribeOn(Schedulers.io())

    fun saveLogged() {
        userRepository.saveLogged()
    }
}
