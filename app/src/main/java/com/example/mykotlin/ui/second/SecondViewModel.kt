package com.example.mykotlin.ui.second

import androidx.annotation.VisibleForTesting
import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SecondViewModel(val noteRepo: NoteRepo) : BaseViewModel<NoteData>() {
    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun loadNote(noteId: String) = launch {
        try {
            noteRepo.getNoteById(noteId).let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                noteRepo.saveNote(it)
            }
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let { noteRepo.deleteNote(it.id) }
            setData(NoteData(isDeleted = true))
        } catch (e: Throwable) {
            setError(e)
        }
    }
}