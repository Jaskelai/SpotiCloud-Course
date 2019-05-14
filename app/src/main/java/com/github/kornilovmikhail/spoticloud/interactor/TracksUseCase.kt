package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.CommonTrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.ArrayList

class TracksUseCase(
    private val tracksSoundcloudUseCase: TracksSoundcloudUseCase,
    private val tracksSpotifyUseCase: TracksSpotifyUseCase,
    private val commonTrackRepository: CommonTrackRepository
) {

    fun getFavoriteTracks(): Single<List<Track>> =
        Single.zip(
            tracksSpotifyUseCase.getFavoriteTracks(),
            tracksSoundcloudUseCase.getFavoriteTracks(),
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

    fun getTrendsTracks(): Single<List<Track>> =
        Single.zip(tracksSpotifyUseCase.getTrendsTracks(),
            tracksSoundcloudUseCase.getTrendsTracks(),
            BiFunction { soundcloudList, spotifyList ->
                val result = ArrayList<Track>()
                result.addAll(soundcloudList)
                result.addAll(spotifyList)
                result
            }
        )

    fun getTrackById(id: Int): Single<Track> =
        commonTrackRepository.findTrackById(id)

    fun getSortedTracks(): Single<List<Track>> = Single.zip(
        tracksSoundcloudUseCase.getFavoriteTracks(),
        tracksSpotifyUseCase.getFavoriteTracks(),
        BiFunction { soundcloudList, spotifyList ->
            val result = ArrayList<Track>()
            result.addAll(soundcloudList)
            result.addAll(spotifyList)
            result.sortedBy { track -> track.title }
            result
        })

}
