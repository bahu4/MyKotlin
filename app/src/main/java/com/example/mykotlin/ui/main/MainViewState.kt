package com.example.mykotlin.ui.main

import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.ui.base.BaseViewState

class MainViewState(val taskList: List<Data>? = null, error: Throwable? = null) :
    BaseViewState<List<Data>?>(taskList, error) {
}