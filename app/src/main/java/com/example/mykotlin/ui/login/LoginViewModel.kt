package com.example.mykotlin.ui.login

import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.errors.NoAuthException
import com.example.mykotlin.ui.base.BaseViewModel

class LoginViewModel : BaseViewModel<Boolean?, LoginViewState>() {
    fun requestUser() {
        NoteRepo.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let { LoginViewState(authenticated = true) }
                ?: let { LoginViewState(error = NoAuthException()) }

        }
    }
}