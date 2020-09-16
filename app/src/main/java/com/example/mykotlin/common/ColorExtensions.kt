package com.example.mykotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Note

fun Note.NoteColor.getColorInt(context: Context): Int =
    ContextCompat.getColor(context, getColorFromRes())

fun Note.NoteColor.getColorFromRes(): Int = when (this) {
    Note.NoteColor.WHITE -> R.color.white
    Note.NoteColor.YELLOW -> R.color.yellow
    Note.NoteColor.GREEN -> R.color.green
    Note.NoteColor.BLUE -> R.color.blue
    Note.NoteColor.RED -> R.color.red
    Note.NoteColor.VIOLET -> R.color.violet
    Note.NoteColor.PINK -> R.color.pink
}
