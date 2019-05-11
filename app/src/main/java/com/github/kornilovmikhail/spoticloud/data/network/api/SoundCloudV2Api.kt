package com.github.kornilovmikhail.spoticloud.data.network.api

import com.github.kornilovmikhail.spoticloud.data.network.model.soundcloud.TrendsTrackSoundCloudResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SoundCloudV2Api {

    @GET("charts")
    fun getTrendsTracks(
        @Query("kind") kind: String,
        @Query("genre") genre: String,
        @Query("oauth_token") token: String
    ): Single<TrendsTrackSoundCloudResponse>
}
