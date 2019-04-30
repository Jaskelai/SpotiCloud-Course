package com.github.kornilovmikhail.spoticloud.data.network.model

import com.google.gson.annotations.SerializedName

data class TrackSoundcloudRemote(
    val id: Long,

    val duration: Int,

    @SerializedName("original_content_size")
    val originalContentSize: Int,

    val genre: String,

    val title: String,

    val description: String,

    val uri: String,

    @SerializedName("permalink_url")
    val permalinkUrl: String,

    @SerializedName("stream_url")
    val streamUrl: String,

    val user: AuthorSoundcloudRemote
)
