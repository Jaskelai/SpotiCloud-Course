package com.github.kornilovmikhail.spoticloud.core.model

data class Track(
    val id: Int,

    val title: String,

    val duration: Int,

    val streamService: StreamServiceEnum,

    val originalContentSize: Int,

    val genre: String,

    val description: String,

    val uri: String,

    val artworkUrl: String?,

    val streamUrl: String,

    val author: Author
)
