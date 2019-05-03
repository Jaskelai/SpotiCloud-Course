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

    private fun getTracksFromNetwork(token: String): Single<List<Track>> = soundCloudApi.getFavoriteTracks(token)
        .map {
            it.map { track -> mapSoundCloudTrackRemoteToTrack(track) }
        }

    private fun getTracksFromDB(): Single<List<Track>> =
        trackDAO.findTracksByStreamService(StreamServiceEnum.SOUNDCLOUD.name)
            .map {
                it.map { track -> mapTrackDBToTrack(track) }
            }

    private fun cacheTracks(tracks: List<Track>): Disposable = Completable.fromAction {
        trackDAO.insertTrackList(tracks.map { mapTrackToTrackDB(it) })
    }.subscribe()

    private fun deleteTracks(): Disposable = Completable.fromAction {
        trackDAO.deleteTracksByStreamService(StreamServiceEnum.SOUNDCLOUD.name)
    }.subscribe()
}
