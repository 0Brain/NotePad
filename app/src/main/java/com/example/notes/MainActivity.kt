package com.example.notes


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapters.RecyclerViewAdapter
import com.example.notes.data.Note
import com.example.notes.viewmodels.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }


    private lateinit var noteViewModel: NoteViewModel

    private var listNotes = ArrayList<Note>()
    private lateinit var searchBarSearchView : SearchView
    private lateinit var recyclerView : RecyclerView
    private var myNotesAdapter:RecyclerViewAdapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchBarSearchView = findViewById(R.id.searchBarSearchView)
        recyclerView = findViewById(R.id.my_recycler_view)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        searchBarSearchView.setOnClickListener {
            searchBarSearchView.isIconified = false
        }

        floatingActionButton.setOnClickListener { view ->
            startActivityForResult(Intent(applicationContext, AddNotesActivity::class.java), ADD_NOTE_REQUEST)
        }



        myNotesAdapter = RecyclerViewAdapter(listNotes) { noteItem: Note -> partItemClicked(noteItem) }

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.setHasFixedSize(true)

        noteViewModel.getAllNotes().observe(this, Observer {
            my_recycler_view.adapter = myNotesAdapter
        })

        searchBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                myNotesAdapter!!.getFilter().filter(newText)
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
        setupBottomAppBarMenuAndNavigation()
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
                data.getStringExtra(AddNotesActivity.EXTRA_DESCRIPTION),
                data.getIntExtra(AddNotesActivity.EXTRA_PRIORITY,1)
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
                data!!.getStringExtra(AddNotesActivity.EXTRA_TITLE),
                data!!.getIntExtra(AddNotesActivity.EXTRA_PRIORITY,1)
            )
            noteViewModel.update(updateNote)
        }

        when(resultCode){
            1->{
                //loadQuery("%")
            }
            2->{
                Snackbar.make(root_layout,R.string.item_removed_message,Snackbar.LENGTH_SHORT).show()
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
        updateNoteActivityIntent.putExtra(AddNotesActivity.EXTRA_PRIORITY, note.priority)
        startActivityForResult(updateNoteActivityIntent,EDIT_NOTE_REQUEST)
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
