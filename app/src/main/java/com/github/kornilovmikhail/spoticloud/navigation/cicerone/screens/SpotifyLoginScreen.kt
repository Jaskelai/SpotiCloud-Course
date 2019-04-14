package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.LoginActivity
import com.spotify.sdk.android.authentication.LoginActivity.REQUEST_KEY
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

class SpotifyLoginScreen @Inject constructor(private val request: AuthenticationRequest?) : SupportAppScreen() {

    override fun getActivityIntent(context: Context?): Intent {
        if (context == null || request == null) {
            throw IllegalArgumentException("Context activity or request can't be null")
        }
        val bundle = Bundle()
        bundle.putParcelable(REQUEST_KEY, request)
        return Intent(context, LoginActivity::class.java).apply {
            putExtra(EXTRA_AUTH_REQUEST, bundle)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    companion object {
        const val EXTRA_AUTH_REQUEST = "EXTRA_AUTH_REQUEST"
    }
}
