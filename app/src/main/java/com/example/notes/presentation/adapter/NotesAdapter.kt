package com.example.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.model.Note


class NotesAdapter(private val clickable: (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getItem(position: Int): Note {
        return notes[position]
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(notes[position], clickable)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(note: Note, clickListener: (Note) -> Unit) {
            val textViewName = itemView.findViewById(R.id.item_name) as TextView
            val textViewAddress = itemView.findViewById(R.id.item_description) as TextView
            textViewName.text = note.noteName
            textViewAddress.text = note.noteDes
            itemView.setOnClickListener { clickListener(note) }
        }
    }
}