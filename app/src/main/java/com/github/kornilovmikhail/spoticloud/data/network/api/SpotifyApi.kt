package com.github.kornilovmikhail.spoticloud.data.network.api

import com.github.kornilovmikhail.spoticloud.data.network.model.spotify.TracksSpotifyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface SpotifyApi {

    @GET("me/tracks")
    fun getFavoriteTracks(@Header("Authorization") token: String): Single<TracksSpotifyResponse>
}
