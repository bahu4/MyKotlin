package com.example.mykotlin.data.provider

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.entity.User
import com.example.mykotlin.data.model.NoteResult
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(id: String)
}