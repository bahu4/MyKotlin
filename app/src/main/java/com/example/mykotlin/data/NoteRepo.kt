package com.example.mykotlin.data

import com.example.mykotlin.data.entity.Data
import java.util.*

object NoteRepo {
    private val notes: List<Data> = listOf(
        Data(
            UUID.randomUUID().toString(),
            "Первая",
            "Это первая задача",
            Data.NoteColor.WHITE
        ),
        Data(
            UUID.randomUUID().toString(),
            "Вторая",
            "Это вторая задача",
            Data.NoteColor.YELLOW
        ),
        Data(
            UUID.randomUUID().toString(),
            "Третья",
            "Это третья задача",
            Data.NoteColor.GREEN
        ),
        Data(
            UUID.randomUUID().toString(),
            "Четвертая",
            "Это четвертая задача",
            Data.NoteColor.BLUE
        )
    )

    fun getTaskList(): List<Data> {
        return notes
    }
}