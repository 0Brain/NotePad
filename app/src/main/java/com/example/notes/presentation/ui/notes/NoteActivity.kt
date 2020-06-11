package com.example.notes.presentation.ui.notes


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.presentation.adapter.NotesAdapter
import com.example.notes.data.model.Note
import com.example.notes.presentation.ui.addnote.AddNotesActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class NoteActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView : RecyclerView
    private val adapter = NotesAdapter{ noteItem: Note -> partItemClicked(noteItem) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomAppBarMenuAndNavigation()
        recyclerView = findViewById(R.id.my_recycler_view)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        floatingActionButton.setOnClickListener { view ->
            startActivityForResult(Intent(applicationContext, AddNotesActivity::class.java),
                ADD_NOTE_REQUEST
            )
        }

        my_recycler_view.layoutManager = GridLayoutManager(this,2)
        my_recycler_view.setHasFixedSize(true)
        my_recycler_view.adapter = adapter

        noteViewModel.getAllNotes().observe(this, Observer <List<Note>> { t->
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
                noteViewModel.delete(adapter.getItem(viewHolder.adapterPosition))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            val newNote = Note(
                data!!.getStringExtra(AddNotesActivity.EXTRA_TITLE),
                data.getStringExtra(AddNotesActivity.EXTRA_DESCRIPTION)
            )
            noteViewModel.insert(newNote)
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(AddNotesActivity.EXTRA_ID,1)

            if(id == -1){
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Note(
                data!!.getStringExtra(AddNotesActivity.EXTRA_TITLE),
                data!!.getStringExtra(AddNotesActivity.EXTRA_TITLE)
            )
            noteViewModel.update(updateNote)
        }

        when(resultCode){
            1->{
                //loadQuery("%")
            }
            2->{
                Snackbar.make(root_layout,
                    R.string.item_removed_message,Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun partItemClicked(note : Note) {
        Toast.makeText(this, "Clicked: ${note.noteName}", Toast.LENGTH_LONG).show()
        // Launch second activity, pass part ID as string parameter
        val updateNoteActivityIntent = Intent(this, AddNotesActivity::class.java)
        updateNoteActivityIntent.putExtra(AddNotesActivity.EXTRA_ID, note.id) //ut name
        updateNoteActivityIntent.putExtra(AddNotesActivity.EXTRA_TITLE, note.noteName)
        updateNoteActivityIntent.putExtra(AddNotesActivity.EXTRA_DESCRIPTION, note.noteDes)
        startActivityForResult(updateNoteActivityIntent,
            EDIT_NOTE_REQUEST
        )
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
}
