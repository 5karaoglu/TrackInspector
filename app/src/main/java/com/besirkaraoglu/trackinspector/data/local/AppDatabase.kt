package com.besirkaraoglu.trackinspector.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.besirkaraoglu.trackinspector.data.entity.FavoriteEntity
import com.besirkaraoglu.trackinspector.data.entity.TrackEntity


@Database(entities = [TrackEntity::class, FavoriteEntity::class],version = 5,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tracksDao(): TracksDao
}