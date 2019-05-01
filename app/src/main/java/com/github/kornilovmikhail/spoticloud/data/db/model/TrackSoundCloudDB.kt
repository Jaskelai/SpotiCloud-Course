package com.github.kornilovmikhail.spoticloud.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.kornilovmikhail.spoticloud.core.model.Author

@Entity(
    tableName = "track_soundcloud"
)
data class TrackSoundCloudDB(
    @ColumnInfo(index = true)
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,

    val duration: Int,

    val originalContentSize: Int,

    val genre: String,

    val description: String,

    val uri: String,

    val permalink: String,

    val streamUrl: String,

    @Embedded(prefix = "author")
    val author: Author
)
