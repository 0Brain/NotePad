package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_notes.*


class AddNotesActivity : AppCompatActivity()  {

    companion object {
        const val EXTRA_ID = "com.example.notes.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.notes.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.notes.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.example.notes.EXTRA_PRIORITY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10


        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            note_name.setText(intent.getStringExtra(EXTRA_TITLE))
            note_detail_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            number_picker_priority.value =  intent.getIntExtra(EXTRA_PRIORITY,1)
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
            putExtra(EXTRA_PRIORITY,number_picker_priority.value)
        }
        setResult(Activity.RESULT_OK,data)
        finish()
    }


    override fun onBackPressed() {
        if(note_name.text.toString().equals("") && note_detail_description.text.toString().equals(""))  {
            setResult(2)
            finish()
        }
        else{
            setResult(1)
            saveNote()
        }
        super.onBackPressed()
    }
}
