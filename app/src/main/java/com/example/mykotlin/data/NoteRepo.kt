package com.example.mykotlin.data

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.provider.FirestoreDataProvider
import com.example.mykotlin.data.provider.RemoteDataProvider

object NoteRepo {
    val remoteProvider: RemoteDataProvider = FirestoreDataProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(id: String) = remoteProvider.deleteNote(id)
}