package com.besirkaraoglu.trackinspector.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runBlockingTest
import androidx.test.filters.SmallTest
import com.besirkaraoglu.trackinspector.data.entity.TrackEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TrackDaoTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: TracksDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.tracksDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun testSaveTrack() = runBlockingTest{
        val trackEntity = TrackEntity(1,213214,true,"none","1122321",
            isLocal = true,
            isPlayable = true,
            name = "Do I Wanna Know",
            previewURL = "",
            trackNumber = 1,
            type = "none",
            uri = "none"
        )
        dao.insertTrack(trackEntity)

        val tracks = dao.getAllTracks()

        assertThat(tracks.isNotEmpty(),`is`(equalTo(true)))
        assertThat(tracks.size,`is`(equalTo(1)))
        assertThat(tracks.contains(trackEntity),`is`(equalTo(true)))
    }


    @Test
    fun testDeleteTrack() = runBlockingTest {
        val trackEntity = TrackEntity(1,213214,true,"none","1122321",
            isLocal = true,
            isPlayable = true,
            name = "Do I Wanna Know",
            previewURL = "",
            trackNumber = 1,
            type = "none",
            uri = "none"
        )
        dao.insertTrack(trackEntity)

        var tracks = dao.getAllTracks()

        assertThat(tracks.isNotEmpty(),`is`(equalTo(true)))
        assertThat(tracks.size,`is`(equalTo(1)))
        assertThat(tracks.contains(trackEntity),`is`(equalTo(true)))

        dao.deleteTrack(trackEntity)

        tracks = dao.getAllTracks()

        assertThat(tracks.isEmpty(),`is`(equalTo(true)))
        assertThat(tracks.size,`is`(equalTo(0)))
        assertThat(tracks.contains(trackEntity),`is`(equalTo(false)))
    }
}