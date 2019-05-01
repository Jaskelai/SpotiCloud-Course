package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackSoundCloudDB
import com.github.kornilovmikhail.spoticloud.data.network.model.AuthorSoundcloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.TokenSoundCloudRemote
import com.github.kornilovmikhail.spoticloud.data.network.model.TrackSoundcloudRemote

fun mapSoundCloudTokenRemoteToToken(soundCloudRemote: TokenSoundCloudRemote): TokenSoundCloud =
    with(soundCloudRemote) {
        TokenSoundCloud(accessToken, expiresIn, refreshToken)
    }

fun mapSoundCloudTrackRemoteToTrack(soundcloudTrack: TrackSoundcloudRemote): Track =
    with(soundcloudTrack) {
        Track(
            0,
            title,
            duration,
            originalContentSize,
            genre,
            description,
            uri,
            permalinkUrl,
            streamUrl,
            mapSoundCloudAuthorRemoteToAuthor(author)
        )
    }

fun mapSoundCloudAuthorRemoteToAuthor(authorSoundcloudRemote: AuthorSoundcloudRemote): Author =
    with(authorSoundcloudRemote) {
        Author(0, username, uri, permalinkUrl, avatarUrl)
    }

fun mapSoundCloudTrackDBToTrack(trackSoundCloudDB: TrackSoundCloudDB) =
    with(trackSoundCloudDB) {
        Track(id, title, duration, originalContentSize, genre, description, uri, permalink, streamUrl, author)
    }

fun mapTrackToSoundCloudTrackDB(track: Track): TrackSoundCloudDB =
    with(track) {
        TrackSoundCloudDB(
            0,
            title,
            duration,
            originalContentSize,
            genre,
            description,
            uri,
            permalink,
            streamUrl,
            author
        )
    }

