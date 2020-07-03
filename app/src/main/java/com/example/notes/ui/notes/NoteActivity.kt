package com.example.notes.ui.notes


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.ui.adapter.NotesAdapter
import com.example.notes.ui.addNote.AddNotesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NoteActivity : AppCompatActivity(),NotesAdapter.ItemClickListener {


    //Lazy Initializations of final objects
    private val mRecyclerView : RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.my_recycler_view)
    }
    private val mAdapter by lazy {
        NotesAdapter(this)
    }
    private val mLayoutManager :StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
    }

    //Hilt ViewModel Injection
    private val noteViewModel:NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.toolbar_main_title)
        setupBottomAppBarMenuAndNavigation()


        floatingActionButton.setOnClickListener {
            val intent = Intent(this@NoteActivity,AddNotesActivity::class.java)
            startActivity(intent)
        }

        recyclerViewSetup()
        noteViewModel.getAllNotes().observe(this, Observer{ t->
            mAdapter.setNotes(t!!)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount - 1)
        })
        onDeleteNote()
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

    private fun recyclerViewSetup(){
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mAdapter
    }

    private fun onDeleteNote(){
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
                    noteViewModel.delete(mAdapter.getItem(viewHolder.adapterPosition))
                }
                Toast.makeText(baseContext, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(my_recycler_view)
    }
}
