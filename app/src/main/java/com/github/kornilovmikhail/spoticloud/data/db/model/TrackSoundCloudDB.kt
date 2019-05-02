package com.github.kornilovmikhail.spoticloud.data.db.model

import androidx.room.*
import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum

@Entity(
    tableName = "track_soundcloud"
)
data class TrackSoundCloudDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    val id: Int,

    val title: String,

    val duration: Int,

    @ColumnInfo(name = "stream_service")
    val streamService: StreamServiceEnum,

    @ColumnInfo(name = "original_content_size")
    val originalContentSize: Int,

    val genre: String,

    val description: String,

    val uri: String,

    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String?,

    @ColumnInfo(name = "stream_url")
    val streamUrl: String,

    @Embedded(prefix = "author_")
    val author: Author
)
