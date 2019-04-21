package com.github.kornilovmikhail.spoticloud.data.mappers

import com.github.kornilovmikhail.spoticloud.core.model.TokenSoundCloud
import com.github.kornilovmikhail.spoticloud.data.network.model.TokenSoundCloudResponse

fun mapSoundCloudTokenResponseToToken(soundCloudResponse: TokenSoundCloudResponse): TokenSoundCloud =
    with(soundCloudResponse) {
        TokenSoundCloud(accessToken, expiresIn, refreshToken)
    }
