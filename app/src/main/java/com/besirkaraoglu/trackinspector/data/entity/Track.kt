package com.besirkaraoglu.trackinspector.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(


    @SerializedName("duration_ms")
    val durationMS: Long,

    val explicit: Boolean,


    val id: String,

    @SerializedName("is_local")
    val isLocal: Boolean,

    @SerializedName("is_playable")
    val isPlayable: Boolean,

    val name: String,

    @SerializedName("track_number")
    val trackNumber: Long,

    val type: String,
    val uri: String,

    val artists: String,
    val popularity: Long,

    val thumbnail: String
): Parcelable


fun Track.asTrackEntity(): TrackEntity =
    TrackEntity(this.durationMS,this.explicit,this.id,this.isLocal,
        this.isPlayable,this.name, this.trackNumber, this.type, this.uri,
        this.artists,this.popularity,this.thumbnail)

fun Track.asFavoriteEntity(): FavoriteEntity =
    FavoriteEntity(this.durationMS,this.explicit,this.id,this.isLocal,
        this.isPlayable,this.name, this.trackNumber, this.type, this.uri,
        this.artists,this.popularity,this.thumbnail)

