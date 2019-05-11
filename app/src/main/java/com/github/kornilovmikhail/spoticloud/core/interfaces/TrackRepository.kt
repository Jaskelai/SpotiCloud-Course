package com.github.kornilovmikhail.spoticloud.core.interfaces

import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Completable
import io.reactivex.Single

interface TrackRepository {

    fun getFavoriteTracks(token: String): Single<List<Track>>

    fun getSearchedTracks(token: String, keywords: String): Single<List<Track>>

    fun addTrackToFav(token: String, id: String): Completable

    fun getTrendsTracks(token: String): Single<List<Track>>
}
