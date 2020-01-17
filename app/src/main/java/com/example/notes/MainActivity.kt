package com.example.notes


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    public var listNotes = ArrayList<Note>()
    private val numberOfColumns = 2
    private lateinit var searchBarSearchView : SearchView
    private lateinit var recyclerView :RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchBarSearchView = findViewById(R.id.searchBarSearchView)
        searchBarSearchView.setOnClickListener {
            searchBarSearchView.isIconified = false
        }
        floatingActionButton.setOnClickListener { view ->
            val intentRegister = Intent(applicationContext, AddNotesActivity::class.java)
            startActivityForResult(intentRegister, 0)
        }
        setupBottomAppBarMenuAndNavigation()
        loadQuery("%")
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
        when(resultCode){
            1->{
                loadQuery("%")
            }
            2->{
                Snackbar.make(root_layout,R.string.item_removed_message,Snackbar.LENGTH_SHORT).show()
            }
        }
    }


     fun loadQuery(title: String) {
        val dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.query(projections, "Title like ?", selectionArgs, "Title")
        listNotes.clear()
        if (cursor.moveToFirst()) {

            do {
                val iD = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(iD, title, description))

            } while (cursor.moveToNext())
        }

         recyclerView = findViewById(R.id.my_recycler_view)

         recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val myNotesAdapter = RecyclerViewAdapter(listNotes) { noteItem:Note->partItemClicked(noteItem)}
         my_recycler_view.adapter = myNotesAdapter

        searchBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                myNotesAdapter!!.getFilter().filter(newText)
                myNotesAdapter.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                myNotesAdapter.notifyDataSetChanged()
                return false
            }
        })
    }


    //    private fun searchContact(keyword: String) {
//        val databaseHelper = DbManager(applicationContext)
//        val notes: List<Note> = databaseHelper.getAllUsers()
//        if (notes != null) {
//            my_recycler_view.setAdapter(ContactListAdapter(applicationContext, notes))
//        }
//    }
    private fun partItemClicked(note : Note) {
        Toast.makeText(this, "Clicked: ${note.noteName}", Toast.LENGTH_LONG).show()
        // Launch second activity, pass part ID as string parameter
        val updateNoteActivityIntent = Intent(this, AddNotesActivity::class.java)
        updateNoteActivityIntent.putExtra(Intent.EXTRA_TEXT, note.noteName) //ut name
        updateNoteActivityIntent.putExtra(Intent.EXTRA_SUBJECT, note.noteDes)
        startActivity(updateNoteActivityIntent)
    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
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
