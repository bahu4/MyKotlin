package com.example.mykotlin.ui.login

import com.example.mykotlin.ui.base.BaseActivity
import com.example.mykotlin.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<Boolean?>() {
    override val viewModel: LoginViewModel by viewModel()
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