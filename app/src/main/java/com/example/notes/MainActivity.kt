package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()
    val numberOfColumns = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener { view ->
            val intentRegister = Intent(applicationContext, AddNotesActivity::class.java)
            startActivityForResult(intentRegister, 1);
        }
        setupBottomAppBarMenuAndNavigation()
        //Load from DB
        LoadQuery("%")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    public fun LoadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Title")
        listNotes.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(ID, Title, Description))

            } while (cursor.moveToNext())
        }

        val recyclerView = findViewById(R.id.my_recycler_view) as RecyclerView



        //adding a layoutmanager

        recyclerView.setLayoutManager(GridLayoutManager(this, numberOfColumns))
        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //adapter instance
        var myNotesAdapter = RecyclerViewAdapter(listNotes,{noteItem:Note->partItemClicked(noteItem)})
        //set adapter
        my_recycler_view.adapter = myNotesAdapter

        //get total number of tasks from ListView
        val total = myNotesAdapter.getItemCount()
        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = "You have $total note(s)"
        }
    }
    private fun partItemClicked(note : Note) {
        Toast.makeText(this, "Clicked: ${note.noteName}", Toast.LENGTH_LONG).show()
        // Launch second activity, pass part ID as string parameter
        val updateNoteActivityIntent = Intent(this, AddNotesActivity::class.java)
        updateNoteActivityIntent.putExtra(Intent.EXTRA_TEXT, note.noteName) //ut name
        updateNoteActivityIntent.putExtra(Intent.EXTRA_SUBJECT, note.noteDes)
        startActivity(updateNoteActivityIntent)
    }

//    override fun onResume() {
//        super.onResume()
//        LoadQuery("%")
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            LoadQuery("%")
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Snackbar.make(root_layout,R.string.item_removed_message,Snackbar.LENGTH_SHORT).show()
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

}
