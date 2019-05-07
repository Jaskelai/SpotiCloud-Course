package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackDB

fun mapTrackDBToTrack(trackDB: TrackDB): Track =
    with(trackDB) {
        Track(
            id,
            sourceId,
            title,
            duration,
            streamService,
            originalContentSize,
            artworkLowSizeUrl,
            artworkUrl,
            streamUrl,
            author
        )
    }

fun mapTrackToTrackDB(track: Track): TrackDB =
    with(track) {
        TrackDB(
            0,
            idSource,
            title,
            duration,
            streamService,
            originalContentSize,
            artworkLowSizeUrl,
            artworkUrl,
            streamUrl,
            author
        )
    }
