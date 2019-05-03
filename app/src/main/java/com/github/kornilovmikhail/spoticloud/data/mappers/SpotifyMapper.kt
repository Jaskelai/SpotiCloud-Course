package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.network.model.spotify.TrackSpotifyRemote

fun mapSpotifyTrackRemoteToTrack(spotifyTrack: TrackSpotifyRemote): Track =
    with(spotifyTrack) {
        Track(
            0,
            title,
            duration,
            StreamServiceEnum.SPOTIFY,
            null,
            album?.image?.filter { it.size == 64 }?.get(0)?.url,
            album?.image?.filter { it.size == 640 }?.get(0)?.url,
            streamUrl,
            Author(artists[0].name, artists[0].link)
        )
    }
