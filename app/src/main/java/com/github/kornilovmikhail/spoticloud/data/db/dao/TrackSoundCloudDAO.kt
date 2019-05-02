package com.github.kornilovmikhail.spoticloud.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackSoundCloudDB
import io.reactivex.Single

@Dao
interface TrackSoundCloudDAO {

    @Query("SELECT * FROM track_soundcloud")
    fun getTrackListSoundCloud(): Single<List<TrackSoundCloudDB>>

    @Insert
    fun insertTrackListSoundCloud(tracks: List<TrackSoundCloudDB>)

    @Query("SELECT * FROM track_soundcloud WHERE id = :id")
    fun findTrackById(id: Int): Single<TrackSoundCloudDB>

    @Query("DELETE FROM track_soundcloud")
    fun deleteAllTracks()
}
