package com.example.notes.presentation.ui.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.data.model.Note
import com.example.notes.data.persistence.local.NoteDatabase
import com.example.notes.data.repository.NoteRepository
import com.example.notes.databinding.ActivityAddNotesBinding
import com.example.notes.presentation.ui.notes.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddNotesActivity : AppCompatActivity()  {

    private lateinit var addNoteBinding: ActivityAddNotesBinding
    @Inject lateinit var repository: NoteRepository
    private val noteViewModel: AddNewNoteViewModel by viewModels()
    private var mNoteId = ADD_NOTE_ID
    companion object{
        const val EXTRA_NOTE = "extra_note"
        const val ADD_NOTE_ID = 1
        const val UPDATE_NOTE_ID =2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNoteBinding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(addNoteBinding.root)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        if(intent!=null && intent.hasExtra(EXTRA_NOTE)){
            mNoteId = UPDATE_NOTE_ID
            val mNoteString = intent.getStringExtra(EXTRA_NOTE)
            noteViewModel.getNote(mNoteString!!).observe(this@AddNotesActivity, Observer {
                addNoteBinding.noteName.setText(it.noteName)
                addNoteBinding.noteDetailDescription.setText(it.noteDes)
            })
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
        val noteName:String = addNoteBinding.noteName.text.toString()
        val noteDescription:String = addNoteBinding.noteDetailDescription.text.toString()
        val note = Note(noteName,noteDescription)
        GlobalScope.launch {
            if(mNoteId == ADD_NOTE_ID){
                noteViewModel.insertNote(note)
            }else if(mNoteId == UPDATE_NOTE_ID){
                note.id = intent.getStringExtra(EXTRA_NOTE)!!
                noteViewModel.updateNote(note)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(addNoteBinding.noteDetailDescription.text.toString().isEmpty() && addNoteBinding.noteName.text.toString().isEmpty()){
            finish()
        }else{
            saveNote()
        }
    }
}
