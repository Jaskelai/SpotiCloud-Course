package com.github.kornilovmikhail.spoticloud.data.network.interceptor

import okhttp3.*

class TokenInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() == 401) {
                    val url = request.url()
                    url.queryParameter("oauth_token")
                    request.url()
        }
        return response
    }
}
