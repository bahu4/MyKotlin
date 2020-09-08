package com.example.mykotlin.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {
    open val viewStateLiveData = MutableLiveData<S>()
    open fun getViewState(): LiveData<S> = viewStateLiveData
}