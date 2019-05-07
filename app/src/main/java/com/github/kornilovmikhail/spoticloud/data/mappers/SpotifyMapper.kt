package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.network.model.spotify.TrackSpotifyRemote

private const val IMAGE_LOW_SIZE = 70
private const val IMAGE_BIG_SIZE = 600

fun mapSpotifyTrackRemoteToTrack(spotifyTrack: TrackSpotifyRemote): Track =
    with(spotifyTrack) {
        Track(
            0,
            id,
            title,
            duration,
            StreamServiceEnum.SPOTIFY,
            null,
            album?.image?.filter { it.size < IMAGE_LOW_SIZE }?.get(0)?.url,
            album?.image?.filter { it.size > IMAGE_BIG_SIZE }?.get(0)?.url,
            streamUrl,
            Author(artists[0].name, artists[0].link)
        )
    }
