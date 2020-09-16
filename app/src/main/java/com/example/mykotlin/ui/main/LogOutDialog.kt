package com.example.mykotlin.ui.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogOutDialog() : DialogFragment() {
    companion object {
        val TAG = LogOutDialog::class.java.name + "TAG"
        fun createInstance(
            onLogOut: (() -> Unit)
        ) = LogOutDialog().apply {
            this.onLogOut = onLogOut
        }
    }

    var onLogOut: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(context)
        .setTitle("Выход")
        .setMessage("Уверены?")
        .setPositiveButton("ДА") { dialog, which -> onLogOut?.invoke() }
        .setNegativeButton("нет") { dialog, which -> dismiss() }
        .create()
}

