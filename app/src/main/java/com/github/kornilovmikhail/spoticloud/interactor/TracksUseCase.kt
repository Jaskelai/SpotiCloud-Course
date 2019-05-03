package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.ArrayList

class TracksUseCase(
    private val tracksSoundcloudUseCase: TracksSoundcloudUseCase,
    private val tracksSpotifyUseCase: TracksSpotifyUseCase
) {

    fun getTracks(): Single<List<Track>> =
        Single.zip(
            tracksSoundcloudUseCase.getTracks(),
            tracksSpotifyUseCase.getTracks(),
            BiFunction { soundcloudList, spotifyList ->
                val result = ArrayList<Track>()
                result.addAll(soundcloudList)
                result.addAll(spotifyList)
                return@BiFunction result
            })
}
