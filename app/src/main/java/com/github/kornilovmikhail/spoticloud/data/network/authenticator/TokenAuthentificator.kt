package com.github.kornilovmikhail.spoticloud.data.network.authenticator

import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import okhttp3.*

class TokenAuthentificator(private val loginSoundcloudUseCase: LoginSoundcloudUseCase) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() == 401) {
            loginSoundcloudUseCase.loadLocalSoundCloudToken()
                .subscribe({

                }, {
                })
        }
        return response
    }
}
