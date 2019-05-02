package com.github.kornilovmikhail.spoticloud.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.kornilovmikhail.spoticloud.data.db.converter.StreamServiceConverter
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackSoundCloudDAO
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackSoundCloudDB

@Database(entities = [TrackSoundCloudDB::class], version = 1)
@TypeConverters(StreamServiceConverter::class)
abstract class AbstractTrackDB : RoomDatabase() {

    abstract fun trackSoundCloudDAO(): TrackSoundCloudDAO
}
