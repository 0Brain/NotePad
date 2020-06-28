package com.example.notes.presentation.ui.notes

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.data.model.Note
import com.example.notes.data.repository.NoteRepository

class NoteViewModel @ViewModelInject constructor(private val noteRepository: NoteRepository):ViewModel(){


    suspend fun delete(note: Note){
        noteRepository.delete(note)
    }

    fun getAllNotes():LiveData<List<Note>>{
        return noteRepository.getAllNotes()
    }
}