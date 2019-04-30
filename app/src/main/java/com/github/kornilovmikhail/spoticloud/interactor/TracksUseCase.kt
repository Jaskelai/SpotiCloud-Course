package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single

class TracksUseCase(
    private val tracksSoundcloudUseCase: TracksSoundcloudUseCase,
    private val tracksSpotifyUseCase: TracksSpotifyUseCase
) {

    fun loadTracks(token: String): Single<List<Track>> =
        tracksSoundcloudUseCase.loadTracks(token)
}
