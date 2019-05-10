package com.github.kornilovmikhail.spoticloud.core.interfaces

import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single

interface CommonTrackRepository {

    fun findTrackById(id: Int): Single<Track>
}
