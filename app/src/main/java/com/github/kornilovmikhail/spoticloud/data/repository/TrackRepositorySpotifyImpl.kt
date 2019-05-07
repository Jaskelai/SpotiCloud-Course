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

    override fun getFavoriteTracks(token: String): Single<List<Track>> =
        getFavoriteTracksFromNetwork(token)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext {
                getFavoriteTracksFromDB()
            }
            .flatMap {
                deleteFavoriteTracks()
                cacheFavoriteTracks(it)
                getFavoriteTracksFromDB()
            }

    override fun getSearchedTracks(token: String, keywords: String): Single<List<Track>> =
        spotifyApi.getSearchedItems("Bearer $token", keywords, TYPE_TRACK)
            .subscribeOn(Schedulers.io())
            .map {
                if (it.tracks.items.isEmpty()) {
                    arrayListOf()
                } else {
                    it.tracks.items.map { track -> mapSpotifyTrackRemoteToTrack(track) }
                }
            }

    override fun addTrackToFav(token: String, id: String): Completable = spotifyApi.addTrackToFav("Bearer $token", id)
        .subscribeOn(Schedulers.io())

    private fun getFavoriteTracksFromNetwork(token: String): Single<List<Track>> =
        spotifyApi.getFavoriteTracks("Bearer $token")
            .map {
                it.items.map { track -> mapSpotifyTrackRemoteToTrack(track.track) }
            }

    private fun getFavoriteTracksFromDB(): Single<List<Track>> =
        trackDAO.findTracksByStreamService(StreamServiceEnum.SPOTIFY.name)
            .map {
                it.map { track -> mapTrackDBToTrack(track) }
            }

    private fun cacheFavoriteTracks(tracks: List<Track>): Disposable = Completable.fromAction {
        trackDAO.insertTrackList(tracks.map { mapTrackToTrackDB(it) })
    }.subscribe()

    private fun deleteFavoriteTracks(): Disposable = Completable.fromAction {
        trackDAO.deleteTracksByStreamService(StreamServiceEnum.SPOTIFY.name)
    }.subscribe()

    companion object {
        private const val TYPE_TRACK = "track"
    }
}
