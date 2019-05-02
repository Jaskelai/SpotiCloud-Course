package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single

class TrackRepositorySpotifyImpl() :
    TrackRepository {

    override fun getTracks(token: String): Single<List<Track>> = Single.just(null)
}
