package com.github.kornilovmikhail.spoticloud.data.network.api

import com.github.kornilovmikhail.spoticloud.data.network.model.spotify.SearchedTracksSpotifyResponse
import com.github.kornilovmikhail.spoticloud.data.network.model.spotify.TracksSpotifyResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SpotifyApi {

    @GET("me/tracks")
    fun getFavoriteTracks(@Header("Authorization") token: String): Single<TracksSpotifyResponse>

    @GET("search")
    fun getSearchedItems(
        @Header("Authorization") token: String,
        @Query("q") keyword: String,
        @Query("type") type: String
    ): Single<SearchedTracksSpotifyResponse>

    @PUT("me/tracks")
    fun addTrackToFav(
        @Header("Authorization") token: String,
        @Query("ids") id: String
    ): Completable

    @GET("playlists/{id}/tracks")
    fun getTrendsTracks(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Single<TracksSpotifyResponse>
}
