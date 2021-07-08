package com.besirkaraoglu.trackinspector.data.remote

import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkDataSource
@Inject constructor(private val webService: WebService){

    @ExperimentalCoroutinesApi
    suspend fun getTracks(): Flow<Resource<List<Track>>> = callbackFlow {
        trySend(
            Resource.Success(
                webService.getTracks()
            )
        ).isSuccess
        awaitClose { close() }
    }
}