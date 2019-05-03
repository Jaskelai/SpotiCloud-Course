package com.github.kornilovmikhail.spoticloud.data.db.model

import androidx.room.Entity

@Entity
data class AuthorDB (
    val username: String,

    val uri: String,

    val avatarUrl: String
)
