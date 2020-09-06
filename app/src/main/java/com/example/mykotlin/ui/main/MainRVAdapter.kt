package com.example.mykotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Data
import kotlinx.android.synthetic.main.item.view.*

class MainRVAdapter : RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Data) = with(itemView) {
            title_view.text = note.title
            new_task.text = note.task
            card_view.setBackgroundColor(note.noteColor)
        }
    }
}