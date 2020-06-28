package com.example.notes.presentation.ui.notes


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.presentation.adapter.NotesAdapter
import com.example.notes.presentation.ui.addnote.AddNotesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NoteActivity : AppCompatActivity(),NotesAdapter.ItemClickListener {

    private lateinit var recyclerView : RecyclerView
    private val adapter = NotesAdapter(this)
    private val noteViewModel:NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomAppBarMenuAndNavigation()
        recyclerView = findViewById(R.id.my_recycler_view)

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@NoteActivity,AddNotesActivity::class.java)
            startActivity(intent)
        }

        my_recycler_view.layoutManager = GridLayoutManager(this,2)
        my_recycler_view.setHasFixedSize(true)
        my_recycler_view.adapter = adapter



        noteViewModel.getAllNotes().observe(this, Observer{ t->
            adapter.setNotes(t!!)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(
            ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                GlobalScope.launch {
                    noteViewModel.delete(adapter.getItem(viewHolder.adapterPosition))
                }
                Toast.makeText(baseContext, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(my_recycler_view)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupBottomAppBarMenuAndNavigation() {
        bottomAppBar.replaceMenu(R.menu.menu_bottom_app_bar)
        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "Clicked menu item 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    Toast.makeText(this, "Clicked menu item 2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3 -> {
                    Toast.makeText(this, "Clicked menu item 3", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        bottomAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Clicked navigation item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClickListener(noteId: String) {
       val updateIntent = Intent(this@NoteActivity,AddNotesActivity::class.java)
        updateIntent.putExtra(AddNotesActivity.EXTRA_NOTE,noteId)
        startActivity(updateIntent)
    }
}
