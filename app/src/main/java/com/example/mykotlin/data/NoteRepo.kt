package com.example.mykotlin.data

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.provider.RemoteDataProvider

class NoteRepo(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun deleteNote(id: String) = remoteProvider.deleteNote(id)
}