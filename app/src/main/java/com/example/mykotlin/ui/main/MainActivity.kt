package com.example.mykotlin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mykotlin.R
import com.example.mykotlin.ui.second.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = MainRVAdapter{
            SecondActivity.start(this, it)
        }

        main_rv.layoutManager = GridLayoutManager(this, 2)
        main_rv.adapter = adapter

        viewModel.viewState().observe(this,
            Observer { state -> state?.let { it -> adapter.tasks = it.taskList } })

        fab.setOnClickListener {
            SecondActivity.start(this)
        }

    }
}