package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TracksSoundcloudUseCase(
    private val tracksRepository: TrackRepository
) {

    fun loadTracks(token: String): Single<List<Track>> = tracksRepository.loadTracks(token).subscribeOn(Schedulers.io())
}
