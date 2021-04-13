package com.example.mykotlin.ui.main

import androidx.annotation.VisibleForTesting
import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(noteRepo: NoteRepo) : BaseViewModel<List<Note>?>() {

    private val repositoryNotes = noteRepo.getNotes()

    init {
        launch {
            repositoryNotes.consumeEach {
                when (it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        repositoryNotes.cancel()
        super.onCleared()
    }
}
