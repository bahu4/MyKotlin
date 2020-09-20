package com.example.mykotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Note
import kotlinx.android.synthetic.main.item.view.*

class MainRVAdapter(val onItemClick: ((Note) -> Unit)? = null) :
    RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
    var tasks: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView) {

            title_view.text = note.title
            new_task.text = note.task
            val color = when (note.noteColor) {
                Note.NoteColor.WHITE -> R.color.white
                Note.NoteColor.YELLOW -> R.color.yellow
                Note.NoteColor.GREEN -> R.color.green
                Note.NoteColor.BLUE -> R.color.blue
                Note.NoteColor.RED -> R.color.red
                Note.NoteColor.VIOLET -> R.color.violet
                Note.NoteColor.PINK -> R.color.pink
            }
            card_view.setBackgroundColor(getColor(itemView.context, color))
            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}