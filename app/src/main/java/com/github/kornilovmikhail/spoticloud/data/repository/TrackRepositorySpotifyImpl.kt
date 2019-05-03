package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackDAO
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSpotifyTrackRemoteToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackDBToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackToTrackDB
import com.github.kornilovmikhail.spoticloud.data.network.api.SpotifyApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TrackRepositorySpotifyImpl(
    private val spotifyApi: SpotifyApi,
    private val trackDAO: TrackDAO
) : TrackRepository {

    override fun getTracks(token: String): Single<List<Track>> =
        getTracksFromNetwork(token)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext {
                getTracksFromDB()
            }
            .flatMap {
                deleteTracks()
                cacheTracks(it)
                getTracksFromDB()
            }

    private fun getTracksFromNetwork(token: String): Single<List<Track>> = spotifyApi.getFavoriteTracks("Bearer $token")
        .map {
            it.items.map { track -> mapSpotifyTrackRemoteToTrack(track.track) }
        }

    private fun getTracksFromDB(): Single<List<Track>> =
        trackDAO.findTracksByStreamService(StreamServiceEnum.SPOTIFY.name)
            .map {
                it.map { track -> mapTrackDBToTrack(track) }
            }

    private fun cacheTracks(tracks: List<Track>): Disposable = Completable.fromAction {
        trackDAO.insertTrackList(tracks.map { mapTrackToTrackDB(it) })
    }.subscribe()

    private fun deleteTracks(): Disposable = Completable.fromAction {
        trackDAO.deleteTracksByStreamService(StreamServiceEnum.SPOTIFY.name)
    }.subscribe()
}
