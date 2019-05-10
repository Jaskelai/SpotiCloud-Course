package com.github.kornilovmikhail.spoticloud.data.repository

import com.github.kornilovmikhail.spoticloud.core.interfaces.CommonTrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackDAO
import com.github.kornilovmikhail.spoticloud.data.mappers.mapTrackDBToTrack
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CommonTrackRepositoryImpl(private val trackDAO: TrackDAO) : CommonTrackRepository {

    override fun findTrackById(id: Int): Single<Track> = trackDAO.findTrackById(id)
        .subscribeOn(Schedulers.io())
        .map {
            mapTrackDBToTrack(it)
        }
}
