package com.github.kornilovmikhail.spoticloud.core.interfaces

import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single

interface TrackRepository {

    fun getTracks(token: String): Single<List<Track>>
}
