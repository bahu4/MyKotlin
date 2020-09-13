package com.example.mykotlin.ui.login

import com.example.mykotlin.ui.base.BaseViewState

class LoginViewState(authenticated: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(authenticated, error)