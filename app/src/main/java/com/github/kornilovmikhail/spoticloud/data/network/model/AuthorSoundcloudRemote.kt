package com.github.kornilovmikhail.spoticloud.data.network.model

import com.google.gson.annotations.SerializedName

data class AuthorSoundcloudRemote(
    val id: Int,

    val username: String,

    val uri: String,

    @SerializedName("permalink_url")
    val permalinkUrl: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
