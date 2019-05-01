package com.github.kornilovmikhail.spoticloud.core.model

import androidx.room.Entity

@Entity
data class Author(
    val id: Int,

    val username: String,

    val uri: String,

    val permalinkUrl: String,

    val avatarUrl: String
)
