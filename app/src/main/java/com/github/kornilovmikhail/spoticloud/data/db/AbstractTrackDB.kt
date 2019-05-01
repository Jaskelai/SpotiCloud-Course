package com.github.kornilovmikhail.spoticloud.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackSoundCloudDAO
import com.github.kornilovmikhail.spoticloud.data.db.model.AuthorDB
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackSoundCloudDB

@Database(entities = [TrackSoundCloudDB::class, AuthorDB::class], version = 1)
abstract class AbstractTrackDB : RoomDatabase() {

    abstract fun trackSoundCloudDAO(): TrackSoundCloudDAO
}
