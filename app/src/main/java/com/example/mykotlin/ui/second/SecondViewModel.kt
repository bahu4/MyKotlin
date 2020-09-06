package com.example.mykotlin.ui.second

import androidx.lifecycle.ViewModel
import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Data

class SecondViewModel : ViewModel() {
    private var pendingNote: Data? = null

    fun save(note: Data) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NoteRepo.saveNote(it)
        }
    }
}