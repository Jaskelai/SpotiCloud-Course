package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackDAO
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackRemoteToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackDBToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackToTrackDB
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TrackRepositorySoundcloudImpl(
    private val soundCloudApi: SoundCloudApi,
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
        soundCloudApi.getSearchedTracks(keywords, token)
            .subscribeOn(Schedulers.io())
            .map {
                if (it.isEmpty()) {
                    arrayListOf()
                } else {
                    it.map { track -> mapSoundCloudTrackRemoteToTrack(track) }
                }
            }

    override fun addTrackToFav(token: String, id: String): Completable = soundCloudApi.addTrackToFav(id, token)
        .subscribeOn(Schedulers.io())

    private fun getFavoriteTracksFromNetwork(token: String): Single<List<Track>> =
        soundCloudApi.getFavoriteTracks(token)
            .map {
                it.map { track -> mapSoundCloudTrackRemoteToTrack(track) }
            }

    private fun getFavoriteTracksFromDB(): Single<List<Track>> =
        trackDAO.findTracksByStreamService(StreamServiceEnum.SOUNDCLOUD.name)
            .map {
                it.map { track -> mapTrackDBToTrack(track) }
            }

    private fun cacheFavoriteTracks(tracks: List<Track>): Disposable = Completable.fromAction {
        trackDAO.insertTrackList(tracks.map { mapTrackToTrackDB(it) })
    }.subscribe()

    private fun deleteFavoriteTracks(): Disposable = Completable.fromAction {
        trackDAO.deleteTracksByStreamService(StreamServiceEnum.SOUNDCLOUD.name)
    }.subscribe()
}
