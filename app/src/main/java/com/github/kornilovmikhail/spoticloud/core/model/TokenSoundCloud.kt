package com.github.kornilovmikhail.spoticloud.core.model

data class TokenSoundCloud(
    val accessToken: String,

    val expiresIn: Int,

    val refreshToken: String
)
