package com.github.kornilovmikhail.spoticloud.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.kornilovmikhail.spoticloud.data.db.model.TrackDB
import io.reactivex.Single

@Dao
interface TrackDAO {

    @Query("SELECT * FROM track")
    fun findAllTracks(): Single<List<TrackDB>>

    @Insert
    fun insertTrackList(tracks: List<TrackDB>)

    @Insert
    fun insertTrack(track: TrackDB)

    @Query("DELETE FROM track")
    fun deleteAllTracks()

    @Query("SELECT * FROM track WHERE id = :id")
    fun findTrackById(id: Int): Single<TrackDB>

    @Query("SELECT * FROM track WHERE stream_service = :service")
    fun findTracksByStreamService(service: String): Single<List<TrackDB>>

    @Query("DELETE FROM track WHERE stream_service = :service")
    fun deleteTracksByStreamService(service: String)
}
