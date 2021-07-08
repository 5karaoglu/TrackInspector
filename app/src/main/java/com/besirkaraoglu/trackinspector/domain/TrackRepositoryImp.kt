package com.besirkaraoglu.trackinspector.domain

import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.data.local.LocalDataSource
import com.besirkaraoglu.trackinspector.data.remote.NetworkDataSource
import com.besirkaraoglu.trackinspector.util.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@InternalCoroutinesApi
@ActivityRetainedScoped
@ExperimentalCoroutinesApi
class TrackRepositoryImp
    @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource): TrackRepository {

    override suspend fun getTracks(): Flow<Resource<List<Track>>> = callbackFlow {
        trySend(getCachedTracks()).onClosed { throw it ?: ClosedSendChannelException("Channel was closed normally") }
            .isSuccess

        networkDataSource.getTracks().collect(object : FlowCollector<Resource<List<Track>>> {
            override suspend fun emit(value: Resource<List<Track>>) {
                when (value) {
                    is Resource.Success -> {
                        for (track in value.data){
                            saveTrack(track)
                        }
                        trySend(getCachedTracks()).isSuccess
                    }
                    is Resource.Failure -> {
                        trySend(getCachedTracks()).isSuccess
                    }
                    Resource.Loading -> {}
                }
            }

        })
        awaitClose{cancel()}

    }

    override suspend fun saveTrack(track: Track) {
        localDataSource.saveTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        localDataSource.deleteTrack(track)
    }

    override suspend fun getCachedTracks(): Resource<List<Track>> {
        return localDataSource.getAllTracks()
    }

    override suspend fun deleteAllCaches() {
        localDataSource.deleteAllTracks()
    }

    override suspend fun isTrackFavorite(track: Track): Boolean {
        return localDataSource.isTrackFavorite(track)
    }

    override suspend fun getFavoriteTracks(): Flow<Resource<List<Track>>> = channelFlow{
        trySend(localDataSource.getAllFavorites())
        awaitClose { close() }
    }

    override suspend fun saveFavoriteTrack(track: Track) {
        localDataSource.saveFavorite(track)
    }

    override suspend fun deleteFavoriteTrack(track: Track) {
        localDataSource.deleteFavorite(track)
    }

    override suspend fun deleteAllFavorites() {
       localDataSource.deleteAllFavorites()
    }
}