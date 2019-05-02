package com.github.kornilovmikhail.spoticloud.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthorDB (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    val id: Int,

    val username: String,

    val uri: String,

    val permalinkUrl: String,

    val avatarUrl: String
)
