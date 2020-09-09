package com.example.mykotlin.data

import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.data.provider.FirestoreDataProvider
import com.example.mykotlin.data.provider.RemoteDataProvider

object NoteRepo {
    val remoteProvider: RemoteDataProvider = FirestoreDataProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun saveNote(note: Data) = remoteProvider.saveNote(note)
}