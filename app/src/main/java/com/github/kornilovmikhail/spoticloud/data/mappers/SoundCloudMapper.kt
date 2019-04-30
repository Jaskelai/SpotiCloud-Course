package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.network.model.TokenSoundCloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.TrackSoundcloudRemote

fun mapSoundCloudTokenResponseToToken(soundCloudRemote: TokenSoundCloudRemote): TokenSoundCloud =
    with(soundCloudRemote) {
        TokenSoundCloud(accessToken, expiresIn, refreshToken)
    }

fun mapSoundCloudTrackToTrack(soundcloudTrack: TrackSoundcloudRemote): Track =
    with(soundcloudTrack) {
        Track(title)
    }
