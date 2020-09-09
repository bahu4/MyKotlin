package com.example.mykotlin.ui.main

import androidx.lifecycle.Observer
import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Data>?, MainViewState>() {
    private val notesObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(taskList = result.data as? List<Data>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = NoteRepo.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}
