package com.example.mykotlin.ui.second

import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.ui.base.BaseViewModel

class SecondViewModel(val noteRepo: NoteRepo) :
    BaseViewModel<SecondViewState.Data, SecondViewState>() {
    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = SecondViewState(SecondViewState.Data(note = note))
    }

    fun loadNote(noteId: String) {
        noteRepo.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value =
                        SecondViewState(SecondViewState.Data(note = result.data as? Note))
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = SecondViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            noteRepo.saveNote(it)
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            noteRepo.deleteNote(it.id).observeForever() { result ->
                result ?: return@observeForever
                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value =
                        SecondViewState(SecondViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value =
                        SecondViewState(error = result.error)
                }
            }
        }
    }
}