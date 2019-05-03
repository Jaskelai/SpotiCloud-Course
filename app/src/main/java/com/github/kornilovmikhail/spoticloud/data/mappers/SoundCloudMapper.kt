package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackDB
import com.github.kornilovmikhail.spoticloud.data.network.model.soundcloud.AuthorSoundCloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.soundcloud.TokenSoundCloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.soundcloud.TrackSoundCloudResponse

fun mapSoundCloudTokenRemoteToToken(soundCloudRemote: TokenSoundCloudRemote): TokenSoundCloud =
    with(soundCloudRemote) {
        TokenSoundCloud(accessToken, expiresIn, refreshToken)
    }

fun mapSoundCloudTrackRemoteToTrack(soundcloudTrack: TrackSoundCloudResponse): Track =
    with(soundcloudTrack) {
        Track(
            0,
            title,
            duration,
            StreamServiceEnum.SOUNDCLOUD,
            originalContentSize,
            null,
            artworkUrl,
            streamUrl,
            mapSoundCloudAuthorRemoteToAuthor(author)
        )
    }

fun mapSoundCloudAuthorRemoteToAuthor(authorSoundCloudRemote: AuthorSoundCloudRemote): Author =
    with(authorSoundCloudRemote) {
        Author(username, avatarUrl)
    }

