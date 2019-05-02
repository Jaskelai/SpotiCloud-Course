package com.github.kornilovmikhail.spoticloud.data.network.model

import com.google.gson.annotations.SerializedName

data class TrackSoundcloudRemote(
    val id: Int,

    val duration: Int,

    @SerializedName("original_content_size")
    val originalContentSize: Int,

    val genre: String,

    val title: String,

    val description: String,

    val uri: String,

    @SerializedName("artwork_url")
    val artworkUrl: String?,

    @SerializedName("stream_url")
    val streamUrl: String,

    @SerializedName("user")
    val author: AuthorSoundcloudRemote
)
