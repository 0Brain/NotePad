package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Note
import kotlinx.android.synthetic.main.notes_list_item.view.*


class RecyclerViewAdapter(private val clickable:(Note)->Unit): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(),Filterable {

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
            val textViewPriority = itemView.findViewById(R.id.text_view_priority) as TextView
            textViewName.text = note.noteName
            textViewAddress.text = note.noteDes
            textViewPriority.text = note.priority.toString()
            itemView.setOnClickListener { clickListener(note) }
        }
    }

    override fun getFilter(): Filter {
        return exampleTest
    }

    private val exampleTest = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<Note>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(notes)
            } else {
                val filterPattern = p0.toString().toLowerCase().trim()
                for (items in notes) {
                    if (items.Text1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(items)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
//            notes.
//            if (results != null) {
//                notes.addAll(results.values as ArrayList<Note>)
//            }
        }
    }
}