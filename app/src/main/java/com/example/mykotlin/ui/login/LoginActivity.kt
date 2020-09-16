package com.example.mykotlin.ui.login

import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.ui.base.BaseActivity
import com.example.mykotlin.ui.main.MainActivity

class LoginActivity : BaseActivity<Boolean?, LoginViewState>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override val layout = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let { startMainActivity() }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}