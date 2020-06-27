package com.example.notes.presentation.ui.addnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.data.model.Note
import com.example.notes.data.repository.NoteRepository

class AddNewNoteViewModel(application: Application,noteId:String) : AndroidViewModel(application){

    private val repository:NoteRepository by lazy {
        NoteRepository(application)
    }

    private val noteId:LiveData<Note> by lazy {
        repository.loadNoteById(noteId)
    }

    fun getNote():LiveData<Note>{
        return noteId
    }
}