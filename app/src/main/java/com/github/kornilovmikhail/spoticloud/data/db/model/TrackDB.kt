package com.github.kornilovmikhail.spoticloud.data.db.model

import androidx.room.*
import com.github.kornilovmikhail.spoticloud.core.model.Author
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum

@Entity(
    tableName = "track"
)
data class TrackDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    val id: Int,

    @ColumnInfo(name = "source_id")
    val sourceId: String,

    val title: String,

    val duration: Int?,

    @ColumnInfo(name = "stream_service")
    val streamService: StreamServiceEnum,

    @ColumnInfo(name = "original_content_size")
    val originalContentSize: Int?,

    @ColumnInfo(name = "artwork_url_lowsize")
    val artworkLowSizeUrl: String?,

    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String?,

    @ColumnInfo(name = "stream_url")
    val streamUrl: String,

    @Embedded(prefix = "author_")
    val author: Author
)
