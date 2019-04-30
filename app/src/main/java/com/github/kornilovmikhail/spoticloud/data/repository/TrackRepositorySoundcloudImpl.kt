package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.mappers.mapSoundCloudTrackToTrack
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import io.reactivex.Single

class TrackRepositorySoundcloudImpl(private val soundCloudApi: SoundCloudApi) : TrackRepository {

    override fun loadTracks(token: String): Single<List<Track>> =
        soundCloudApi.getFavoriteTracks(token)
            .map {
                it.map { track -> mapSoundCloudTrackToTrack(track) }
            }
}
