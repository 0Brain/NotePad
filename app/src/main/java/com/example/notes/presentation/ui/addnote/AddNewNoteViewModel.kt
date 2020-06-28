package com.example.notes.presentation.ui.addnote

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.data.model.Note
import com.example.notes.data.repository.NoteRepository
import javax.inject.Inject

class AddNewNoteViewModel constructor(private val repository: NoteRepository,noteId:String) : ViewModel(){

//    private val noteId:LiveData<Note> by lazy {
//        repository.loadNoteById(noteId)
//    }
//
//    fun getNote():LiveData<Note>{
//        return noteId
//    }
}