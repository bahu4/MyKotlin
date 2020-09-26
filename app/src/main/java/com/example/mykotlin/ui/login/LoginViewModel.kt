package com.example.mykotlin.ui.login

import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.errors.NoAuthException
import com.example.mykotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val noteRepo: NoteRepo) : BaseViewModel<Boolean?>() {
    fun requestUser() = launch {
        noteRepo.getCurrentUser()?.let {
            setData(true)
        } ?: let { setError(NoAuthException()) }
    }
}