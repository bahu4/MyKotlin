package com.example.mykotlin.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import com.example.mykotlin.ui.base.BaseActivity
import com.example.mykotlin.ui.base.BaseViewModel
import com.example.mykotlin.ui.second.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Data>?, MainViewState>() {
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
}