package com.besirkaraoglu.trackinspector.domain

import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    suspend fun getTracks(): Flow<Resource<List<Track>>>
    suspend fun saveTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    suspend fun getCachedTracks(): Resource<List<Track>>
    suspend fun deleteAllCaches()
    suspend fun isTrackFavorite(track: Track): Boolean
    suspend fun getFavoriteTracks(): Flow<Resource<List<Track>>>
    suspend fun saveFavoriteTrack(track: Track)
    suspend fun deleteFavoriteTrack(track: Track)
    suspend fun deleteAllFavorites()

}