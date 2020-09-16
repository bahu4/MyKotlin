package com.example.mykotlin.ui.second

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.ui.base.BaseViewState

class SecondViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<SecondViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}