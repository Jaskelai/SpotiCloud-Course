package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackSoundCloudDAO
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackDBToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackRemoteToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackToSoundCloudTrackDB
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TrackRepositorySoundcloudImpl(
    private val soundCloudApi: SoundCloudApi,
    private val trackSoundCloudDAO: TrackSoundCloudDAO
) : TrackRepository {

    override fun getTracks(token: String): Single<List<Track>> =
        getTracksFromNetwork(token)
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext { getTracksFromDB() }
            .flatMap {
                deleteTracks()
                cacheTracks(it)
                getTracksFromDB()
            }

    private fun getTracksFromDB(): Single<List<Track>> = trackSoundCloudDAO.getTrackListSoundCloud()
        .map {
            it.map { track -> mapSoundCloudTrackDBToTrack(track) }
        }

    private fun getTracksFromNetwork(token: String): Single<List<Track>> = soundCloudApi.getFavoriteTracks(token)
        .map {
            it.map { track -> mapSoundCloudTrackRemoteToTrack(track) }
        }

    private fun cacheTracks(tracks: List<Track>): Disposable = Completable.fromAction {
        trackSoundCloudDAO.insertTrackListSoundCloud(tracks.map { mapTrackToSoundCloudTrackDB(it) })
    }.subscribe()

    private fun deleteTracks(): Disposable = Completable.fromAction {
        trackSoundCloudDAO.deleteAllTracks()
    }.subscribe()
}
