package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Note
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class NotesAdapter constructor(listener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<Note> = ArrayList()
    private var itemClickListener = listener


    interface ItemClickListener{
        fun onItemClickListener(noteId:String)
    }

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
        holder.bindItems(notes[position])
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        fun bindItems(note: Note) {
            val textViewName = itemView.findViewById(R.id.item_name) as TextView
            val textViewAddress = itemView.findViewById(R.id.item_description) as TextView
            textViewName.text = note.noteName
            textViewAddress.text = note.noteDes
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val note = notes[adapterPosition].id
            itemClickListener.onItemClickListener(note)
        }
    }
}