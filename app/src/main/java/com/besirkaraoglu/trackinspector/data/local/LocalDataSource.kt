package com.besirkaraoglu.trackinspector.data.local

import androidx.lifecycle.LiveData
import com.besirkaraoglu.trackinspector.data.entity.*
import com.besirkaraoglu.trackinspector.util.Resource
import javax.inject.Inject

class LocalDataSource
@Inject constructor(private val tracksDao: TracksDao){

    suspend fun getAllTracks(): Resource<List<Track>> {
        return Resource.Success(tracksDao.getAllTracks().asTrackList())
    }

    suspend fun saveTrack(track: Track){
        tracksDao.insertTrack(track.asTrackEntity())
    }

    suspend fun deleteTrack(track: Track){
        tracksDao.deleteTrack(track.asTrackEntity())
    }

    suspend fun deleteAllTracks(){
        tracksDao.deleteAllTracks()
    }

    suspend fun getAllFavorites():Resource<List<Track>>{
        return Resource.Success(tracksDao.getAllFavorites().asTrackList())
    }

    suspend fun isTrackFavorite(track: Track): Boolean{
        return tracksDao.getFavoriteTrackById(track.id) != null
    }

    suspend fun getFavoriteTrackById(track: Track): FavoriteEntity? {
        return tracksDao.getFavoriteTrackById(track.id)
    }

    suspend fun saveFavorite(track: Track){
        tracksDao.insertFavorite(track.asFavoriteEntity())
    }

    suspend fun deleteFavorite(track: Track){
        tracksDao.deleteFavorite(track.asFavoriteEntity())
    }

    suspend fun deleteAllFavorites(){
        tracksDao.deleteAllFavorites()
    }
}