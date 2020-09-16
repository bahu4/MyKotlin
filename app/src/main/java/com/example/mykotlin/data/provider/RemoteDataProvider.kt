package com.example.mykotlin.data.provider

import androidx.lifecycle.LiveData
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.entity.User
import com.example.mykotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(id: String): LiveData<NoteResult>
}