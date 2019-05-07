package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.ArrayList

class TracksUseCase(
    private val tracksSoundcloudUseCase: TracksSoundcloudUseCase,
    private val tracksSpotifyUseCase: TracksSpotifyUseCase
) {

    fun getFavoriteTracks(): Single<List<Track>> =
        Single.zip(
            tracksSoundcloudUseCase.getFavoriteTracks(),
            tracksSpotifyUseCase.getFavoriteTracks(),
            BiFunction { soundcloudList, spotifyList ->
                val result = ArrayList<Track>()
                result.addAll(soundcloudList)
                result.addAll(spotifyList)
                result
            })

    fun getSearchedTracks(keyword: String): Single<List<Track>> =
        Single.zip(
            tracksSoundcloudUseCase.getSearchedTracks(keyword),
            tracksSpotifyUseCase.getSearchedTracks(keyword),
            BiFunction { soundcloudList, spotifyList ->
                val result = ArrayList<Track>()
                result.addAll(soundcloudList)
                result.addAll(spotifyList)
                result
            }
        )

    fun addTrackToFav(track: Track?): Completable {
        return if (track != null) {
            when (track.streamService) {
                StreamServiceEnum.SOUNDCLOUD -> tracksSoundcloudUseCase.addTrackToFav(track)
                StreamServiceEnum.SPOTIFY -> tracksSpotifyUseCase.addTrackToFav(track)
            }
        } else {
            Completable.complete()
        }
    }
}
