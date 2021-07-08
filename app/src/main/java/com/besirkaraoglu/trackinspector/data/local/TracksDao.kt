package com.besirkaraoglu.trackinspector.data.local

import androidx.room.*
import com.besirkaraoglu.trackinspector.data.entity.FavoriteEntity
import com.besirkaraoglu.trackinspector.data.entity.TrackEntity

@Dao
interface TracksDao {


    @Query("SELECT * FROM table_tracks")
    suspend fun getAllTracks(): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Delete
    suspend fun deleteTrack(trackEntity: TrackEntity)

    @Query("DELETE FROM table_tracks")
    suspend fun deleteAllTracks()

    @Query("SELECT * FROM table_favorites")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM table_favorites WHERE track_id = :trackId")
    suspend fun getFavoriteTrackById(trackId: String): FavoriteEntity?

    @Query("DELETE FROM table_favorites")
    suspend fun deleteAllFavorites()



}