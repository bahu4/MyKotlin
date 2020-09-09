package com.example.mykotlin.ui.second

import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.ui.base.BaseViewModel

class SecondViewModel : BaseViewModel<Data?, SecondViewState>() {
    private var pendingNote: Data? = null

    fun save(note: Data) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        NoteRepo.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = SecondViewState(note = result.data as? Data)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = SecondViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            NoteRepo.saveNote(it)
        }
    }
}