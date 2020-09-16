package com.example.mykotlin.ui.second

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import com.example.mykotlin.R
import com.example.mykotlin.common.getColorInt
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_second.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SecondActivity : BaseActivity<SecondViewState.Data, SecondViewState>() {
    companion object {
        private val EXTRA_NOTE = SecondActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        fun start(context: Context, noteId: String? = null) =
            Intent(context, SecondActivity::class.java)
                .run {
                    noteId?.let { putExtra(EXTRA_NOTE, noteId) }
                    context.startActivity(this)
                }
    }

    private var note: Note? = null
    override val viewModel: SecondViewModel by viewModel()
    override val layout = R.layout.activity_second
    private var color: Note.NoteColor = Note.NoteColor.WHITE

    val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let { id -> viewModel.loadNote(id) }
        init()
    }

    override fun renderData(data: SecondViewState.Data) {
        if (data.isDeleted) finish()
        this.note = data.note
        init()
    }

    private fun saveNote() {
        if (title_text.text == null || title_text.text!!.length < 3) return

        note = note?.copy(
            title = title_text.text.toString(),
            task = task_text.text.toString(),
            lastChange = Date(),
            noteColor = color
        ) ?: Note(
            UUID.randomUUID().toString(),
            title_text.text.toString(),
            task_text.text.toString()
        )
        note?.let { viewModel.save(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note, menu).let {
        true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.add_note -> onBackPressed().let { true }
        R.id.delete -> delete().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    fun delete() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.note_del))
            .setPositiveButton(R.string.yes) { dialog, which -> viewModel.deleteNote() }
            .setNegativeButton(R.string.no) { dialog, which -> dialog.dismiss() }
            .show()
    }

    private fun init() {
        title_text.removeTextChangedListener(textChangeListener)
        task_text.removeTextChangedListener(textChangeListener)
        note?.let { note ->
            title_text.setText(note.title)
            task_text.setText(note.task)
            title_text.setSelection(title_text.text?.length ?: 0)
            task_text.setSelection(task_text.text?.length ?: 0)
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChange)
            appbar.setBackgroundColor(note.noteColor.getColorInt(this))
        } ?: let { supportActionBar?.title = getString(R.string.new_note_title) }
        title_text.addTextChangedListener(textChangeListener)
        task_text.addTextChangedListener(textChangeListener)
    }
}