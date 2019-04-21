package com.github.kornilovmikhail.spoticloud.data.di

import android.net.Uri
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SpotifyModule {

    @Provides
    @ApplicationScope
    fun provideAuthenticationRequest(
        typeToken: AuthenticationResponse.Type, @Named("SPOTIFY_URI") redirectUri: Uri
    ): AuthenticationRequest =
        AuthenticationRequest.Builder(CLIENT_ID, typeToken, redirectUri.toString())
            .setShowDialog(false)
            .setScopes(arrayOf(SCOPE_USER))
            .setCampaign(CAMPAIGN)
            .build()

    @Provides
    @ApplicationScope
    @Named("SPOTIFY_URI")
    fun provideRedirectUri(): Uri =
        Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTH)
            .build()

    @Provides
    @ApplicationScope
    fun provideTypeToken(): AuthenticationResponse.Type = AuthenticationResponse.Type.TOKEN

    companion object {
        private const val CAMPAIGN = "your-campaign-token"
        private const val SCOPE_USER = "user-read-email"
        private const val SCHEME = "spotify-sdk"
        private const val AUTH = "auth"
        private const val CLIENT_ID = "478660842d684ce584e4773733ac3180"
    }
}
