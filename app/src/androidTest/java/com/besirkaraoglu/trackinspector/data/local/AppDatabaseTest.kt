package com.besirkaraoglu.trackinspector.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.besirkaraoglu.trackinspector.data.entity.TrackEntity
import org.hamcrest.CoreMatchers.`is`
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp(){
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown(){
        appDatabase.close()
    }

    @Test
    fun testIsDatabaseNotOpen(){
        assertThat(appDatabase.isOpen,`is`(equalTo(false)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseOpen() = runBlockingTest{
        executeDatabaseFunction()
        assertThat(appDatabase.isOpen,`is`(equalTo(true)))
    }

    @Test
    fun testDatabaseVersionIsCurrent() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(appDatabase.openHelper.readableDatabase.version,`is`(equalTo(1)))
    }

    @Test
    fun testDatabasePathIsMemory() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(appDatabase.openHelper.readableDatabase.path,`is`(equalTo(":memory:")))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun executeDatabaseFunction() = runBlockingTest {
        val trackEntity = TrackEntity(1,213214,true,"none","1122321",
            isLocal = true,
            isPlayable = true,
            name = "Do I Wanna Know",
            previewURL = "",
            trackNumber = 1,
            type = "none",
            uri = "none"
        )

        appDatabase.tracksDao().insertTrack(trackEntity)
    }
}