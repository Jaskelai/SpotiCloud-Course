package com.github.kornilovmikhail.spoticloud.data.network.api

import com.github.kornilovmikhail.spoticloud.data.network.model.TokenSoundCloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.TrackSoundcloudRemote
import io.reactivex.Single
import retrofit2.http.*

interface SoundCloudApi {

    @FormUrlEncoded
    @POST("/oauth2/token")
    fun getToken(
        @Field("username") email: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): Single<TokenSoundCloudRemote>

    @GET("/me/favorites")
    fun getFavoriteTracks(
        @Query("oauth_token") token: String
    ): Single<List<TrackSoundcloudRemote>>
}
