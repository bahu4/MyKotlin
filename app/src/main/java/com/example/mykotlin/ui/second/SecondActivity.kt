package com.example.mykotlin.ui.second

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import java.text.SimpleDateFormat
import java.util.*

class SecondActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_NOTE = SecondActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        fun start(context: Context, note: Data? = null) =
            Intent(context, SecondActivity::class.java)
                .run {
                    note?.let { putExtra(EXTRA_NOTE, note) }
                    context.startActivity(this)
                }
    }

    private var note: Data? = null
    lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        
    }
}