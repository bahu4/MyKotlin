package com.example.mykotlin.data

import com.example.mykotlin.data.entity.Data

object NoteRepo {
    private val notes: List<Data> = listOf(
        Data(
            "Первая",
            "Это первая задача",
            0xffffeb3b.toInt()
        ),
        Data(
            "Вторая",
            "Это вторая задача",
            0xfff02292.toInt()
        ),
        Data(
            "Третья",
            "Это третья задача",
            0xff4db6ac.toInt()
        ),
        Data(
            "Четвертая",
            "Это четвертая задача",
            0xff9575cd.toInt()
        )
    )

    fun getTaskList(): List<Data> {
        return notes
    }
}