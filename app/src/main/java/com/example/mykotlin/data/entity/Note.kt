package com.example.mykotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: String = "",
    val title: String = "",
    val task: String = "",
    val noteColor: NoteColor = NoteColor.WHITE,
    val lastChange: Date = Date()
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Note
        if (id != other.id) return false
        return true
    }

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
