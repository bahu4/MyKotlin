package com.example.mykotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.data.entity.Data
import java.util.*

object NoteRepo {
    private val notesLiveData = MutableLiveData<List<Data>>()
    private val notes = mutableListOf(
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
        ),
        Data(
            UUID.randomUUID().toString(),
            "Пятая",
            "Это пятая задача",
            Data.NoteColor.RED
        ),
        Data(
            UUID.randomUUID().toString(),
            "Шестая",
            "Это шестая задача",
            Data.NoteColor.VIOLET
        ),
        Data(
            UUID.randomUUID().toString(),
            "Седьмая",
            "Это седьмая задача",
            Data.NoteColor.PINK
        )
    )

    init {
        notesLiveData.value = notes
    }

    fun getTaskList(): LiveData<List<Data>> {
        return notesLiveData
    }

    fun saveNote(note: Data) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Data) {
        for (i in notes.indices)
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        notes.add(note)
    }
}