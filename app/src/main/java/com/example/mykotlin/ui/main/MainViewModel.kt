package com.example.mykotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlin.data.NoteRepo

class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(NoteRepo.getTaskList())
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}