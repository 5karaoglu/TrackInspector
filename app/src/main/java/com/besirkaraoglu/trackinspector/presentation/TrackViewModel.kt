package com.besirkaraoglu.trackinspector.presentation

import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.besirkaraoglu.trackinspector.application.ToastHelper
import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.domain.TrackRepositoryImp
import com.besirkaraoglu.trackinspector.util.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class TrackViewModel
@ExperimentalCoroutinesApi
@Inject constructor(private val trackRepository: TrackRepositoryImp,
                    private val toastHelper: ToastHelper,
                    private val savedStateHandle: SavedStateHandle

):ViewModel(){

    private val currentTrackName = savedStateHandle.getLiveData<String>("trackName","Do I Wanna Know")

    fun setTrack(trackName:String){
        currentTrackName.value = trackName
    }

    @ExperimentalCoroutinesApi
    val fetchTrackList =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                trackRepository.getTracks().collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    @ExperimentalCoroutinesApi
    val fetchFavorites =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)

            try {
                trackRepository.getFavoriteTracks().collect{
                    emit(it)
                }
            }catch (e:Exception){
                emit(Resource.Failure(e))
            }
        }


   @ExperimentalCoroutinesApi
   fun saveOrDeleteFavorite(track: Track) = viewModelScope.launch {
       if (trackRepository.isTrackFavorite(track)){
            trackRepository.deleteFavoriteTrack(track)
            toastHelper.sendToast("Track deleted from favorites !")
       }else{
           trackRepository.saveFavoriteTrack(track)
           toastHelper.sendToast("Track added to favorites !")
       }
   }


    @ExperimentalCoroutinesApi
    fun saveTrack(track:Track) = viewModelScope.launch {
        trackRepository.saveTrack(track)
        toastHelper.sendToast("Saved successfully !")
    }

    @ExperimentalCoroutinesApi
    fun deleteTrack(track: Track) = viewModelScope.launch {
        trackRepository.deleteTrack(track)
        toastHelper.sendToast("Deleted successfully !")
    }

    @ExperimentalCoroutinesApi
    suspend fun isTrackFavorite(track: Track): Boolean =
        trackRepository.isTrackFavorite(track)

    @ExperimentalCoroutinesApi
    fun saveFavoriteTrack(track: Track) = viewModelScope.launch {
        trackRepository.saveFavoriteTrack(track)
    }

    @ExperimentalCoroutinesApi
    fun deleteFavoriteTrack(track: Track) = viewModelScope.launch {
        trackRepository.deleteFavoriteTrack(track)
    }

    @ExperimentalCoroutinesApi
    fun deleteAllTracks() = viewModelScope.launch {
        trackRepository.deleteAllCaches()
    }

    @ExperimentalCoroutinesApi
    fun deleteAllFavorites() = viewModelScope.launch {
        trackRepository.deleteAllFavorites()
    }

    fun getBitmapFromUrl(str:String) = viewModelScope.launch {
        runCatching {
            val url = URL(str)
            val bitmap = BitmapFactory.decodeStream(url.openStream())


        }

    }
}