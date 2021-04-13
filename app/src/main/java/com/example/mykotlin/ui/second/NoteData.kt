package com.example.mykotlin.ui.second

import com.example.mykotlin.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)