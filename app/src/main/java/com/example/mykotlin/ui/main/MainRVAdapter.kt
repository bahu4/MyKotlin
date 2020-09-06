package com.example.mykotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import kotlinx.android.synthetic.main.item.view.*

class MainRVAdapter(val onItemClick: ((Data) -> Unit)? = null) :
    RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
    var tasks: List<Data> = listOf()
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
        fun bind(note: Data) = with(itemView) {

            title_view.text = note.title
            new_task.text = note.task
            val color = when (note.noteColor) {
                Data.NoteColor.WHITE -> R.color.white
                Data.NoteColor.YELLOW -> R.color.yellow
                Data.NoteColor.GREEN -> R.color.green
                Data.NoteColor.BLUE -> R.color.blue
                Data.NoteColor.RED -> R.color.red
                Data.NoteColor.VIOLET -> R.color.violet
                Data.NoteColor.PINK -> R.color.pink
            }
            card_view.setBackgroundColor(getColor(itemView.context, color))
            card_view.setOnClickListener{
                onItemClick?.invoke(note)
            }
        }
    }
}