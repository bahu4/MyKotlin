package com.example.mykotlin.ui.second

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import kotlinx.android.synthetic.main.activity_second.*
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

    val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }
    }

    private fun saveNote() {
        if (title_text.text == null || title_text.text!!.length < 3) return

        note = note?.copy(
            title = title_text.text.toString(),
            task = task_text.text.toString(),
            lastChange = Date()
        ) ?: Data(
            UUID.randomUUID().toString(),
            title = title_text.text.toString(),
            task = task_text.text.toString(),
        )
        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = note?.let { note ->
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChange)
        } ?: let {
            getString(R.string.new_note_title)
        }
        init()
    }

    private fun init() {
        title_text.removeTextChangedListener(textChangeListener)
        task_text.removeTextChangedListener(textChangeListener)
        note?.let { note ->
            title_text.setText(note.title)
            task_text.setText(note.task)

            val color = when (note.noteColor) {
                Data.NoteColor.WHITE -> R.color.white
                Data.NoteColor.YELLOW -> R.color.yellow
                Data.NoteColor.GREEN -> R.color.green
                Data.NoteColor.BLUE -> R.color.blue
                Data.NoteColor.RED -> R.color.red
                Data.NoteColor.VIOLET -> R.color.violet
                Data.NoteColor.PINK -> R.color.pink
            }
            textInputLayout2.setBackgroundColor(color)
        }
        title_text.addTextChangedListener(textChangeListener)
        task_text.addTextChangedListener(textChangeListener)
    }
}