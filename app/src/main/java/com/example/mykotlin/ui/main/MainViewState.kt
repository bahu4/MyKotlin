package com.example.mykotlin.ui.main

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.ui.base.BaseViewState

class MainViewState(val taskList: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(taskList, error) {
}