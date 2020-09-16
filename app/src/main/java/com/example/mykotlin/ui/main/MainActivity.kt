package com.example.mykotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.ui.base.BaseActivity
import com.example.mykotlin.ui.base.BaseViewModel
import com.example.mykotlin.ui.login.LoginActivity
import com.example.mykotlin.ui.second.SecondActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Data>?, MainViewState>() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val viewModel: BaseViewModel<List<Data>?, MainViewState> by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layout = R.layout.activity_main
    lateinit var adapter: MainRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_rv.layoutManager = GridLayoutManager(this, 2)
        adapter = MainRVAdapter { note ->
            SecondActivity.start(this, note.id)
        }
        main_rv.adapter = adapter
        fab.setOnClickListener {
            SecondActivity.start(this)
        }
    }

    override fun renderData(data: List<Data>?) {
        data?.let { adapter.tasks = it }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.exit -> showLogoutDialog().let { true }
        else -> false
    }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogOutDialog.TAG) ?: LogOutDialog.createInstance {
            onLogout()
        }.show(supportFragmentManager, LogOutDialog.TAG)
    }

    fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }
}