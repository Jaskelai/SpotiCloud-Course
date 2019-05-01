package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackSoundCloudDAO
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackDBToTrack
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackRemoteToTrack
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import io.reactivex.Single

class TrackRepositorySoundcloudImpl(
    private val soundCloudApi: SoundCloudApi,
    private val trackSoundCloudDAO: TrackSoundCloudDAO
) : TrackRepository {
    private var isFirst = true

    override fun loadTracks(token: String): Single<List<Track>> {
        if (isFirst) {
            isFirst = false
            return trackSoundCloudDAO.getTrackListSoundCloud()
                .map {
                    it.map { track -> mapSoundCloudTrackDBToTrack(track) }
                }
        }
        return soundCloudApi.getFavoriteTracks(token)
            .map {
                it.map { track -> mapSoundCloudTrackRemoteToTrack(track) }
            }
    }
}
