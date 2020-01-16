package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val list:ArrayList<Note>, private val clickable:(Note)->Unit): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(),Filterable {

    private val filteredItems = ArrayList<Note>();

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position], clickable)
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

    override fun getFilter(): Filter {
        return exampleTest;
    }

    private val exampleTest = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<Note>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(list)
            } else {
                val filterPattern = p0.toString().toLowerCase().trim()
                for (items in list) {
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
            filteredItems.clear()
            filteredItems.addAll(results?.values as ArrayList<Note>)
        }

    }
}