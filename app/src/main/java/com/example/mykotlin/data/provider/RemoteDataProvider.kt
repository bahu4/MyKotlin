package com.example.mykotlin.data.provider

import androidx.lifecycle.LiveData
import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Data): LiveData<NoteResult>
}