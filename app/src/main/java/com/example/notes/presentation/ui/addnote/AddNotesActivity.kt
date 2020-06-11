package com.example.notes.presentation.ui.addnote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import kotlinx.android.synthetic.main.activity_add_notes.*


class AddNotesActivity : AppCompatActivity()  {

    companion object {
        const val EXTRA_ID = "com.example.notes.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.notes.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.notes.EXTRA_DESCRIPTION"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_grey)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            note_name.setText(intent.getStringExtra(EXTRA_TITLE))
            note_detail_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
        }else{
            title = "Add note"
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun saveNote(){
        val data = Intent().apply {
            putExtra(EXTRA_TITLE,note_name.text.toString())
            putExtra(EXTRA_DESCRIPTION,note_detail_description.text.toString())
        }
        setResult(Activity.RESULT_OK,data)
        finish()
    }


    override fun onBackPressed() {
        if(note_name.text.toString() == "" && note_detail_description.text.toString() == "")  {
            setResult(2)
            finish()
        }
        else{
            saveNote()
        }
        super.onBackPressed()
    }
}
