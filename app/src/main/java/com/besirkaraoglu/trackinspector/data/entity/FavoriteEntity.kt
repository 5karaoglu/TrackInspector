package com.besirkaraoglu.trackinspector.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favorites")
data class FavoriteEntity (


    @ColumnInfo(name = "duration_ms")
    val durationMS: Long,

    val explicit: Boolean,

    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val trackId: String,

    @ColumnInfo(name = "is_local")
    val isLocal: Boolean,

    @ColumnInfo(name = "is_playable")
    val isPlayable: Boolean,

    val name: String,

    @ColumnInfo(name = "track_number")
    val trackNumber: Long,

    val type: String,
    val uri: String,

    val artists: String,
    val popularity: Long,

    val thumbnail: String
)

fun List<FavoriteEntity>.asTrackList(): List<Track> = this.map {
    Track(it.durationMS,it.explicit,it.trackId,it.isLocal,
        it.isPlayable,it.name,it.trackNumber,it.type,it.uri,it.artists,it.popularity,it.thumbnail)
}


