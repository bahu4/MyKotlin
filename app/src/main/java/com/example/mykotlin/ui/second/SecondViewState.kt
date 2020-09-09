package com.example.mykotlin.ui.second

import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.ui.base.BaseViewState

class SecondViewState(val note: Data? = null, error: Throwable? = null) :
    BaseViewState<Data?>(note, error) {
}