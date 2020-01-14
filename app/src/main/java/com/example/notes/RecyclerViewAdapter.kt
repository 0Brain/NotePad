package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val list:ArrayList<Note>,val clickable:(Note)->Unit): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position],clickable)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bindItems(note: Note,clickListener:(Note)->Unit) {
            val textViewName = itemView.findViewById(R.id.item_name) as TextView
            val textViewAddress  = itemView.findViewById(R.id.item_description) as TextView
            textViewName.text = note.noteName
            textViewAddress.text = note.noteDes
            itemView.setOnClickListener { clickListener(note)}
        }
    }
}