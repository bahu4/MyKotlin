package com.example.mykotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val id: String,
    val title: String,
    val task: String,
    val noteColor: NoteColor = NoteColor.WHITE,

    ): Parcelable {
    enum class NoteColor {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }

}
