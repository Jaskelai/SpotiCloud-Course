package com.github.kornilovmikhail.spoticloud.core.model

data class Track(
    val id: Int,

    val title: String,

    val duration: Int?,

    val streamService: StreamServiceEnum,

    val originalContentSize: Int?,

    val artworkLowSizeUrl: String?,

    val artworkUrl: String?,

    val streamUrl: String,

    val author: Author
)
