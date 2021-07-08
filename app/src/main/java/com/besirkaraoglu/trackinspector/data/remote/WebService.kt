package com.besirkaraoglu.trackinspector.data.remote

import com.besirkaraoglu.trackinspector.data.entity.Track
import retrofit2.http.GET

interface WebService {

    @GET("v3/1242b742-76b3-4a0c-8dbe-30c595ffd526")
    suspend fun getTracks(): List<Track>
}