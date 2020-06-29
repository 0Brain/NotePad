package com.example.notes.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.model.Note
import com.example.notes.repository.NoteRepository

class NoteViewModel @ViewModelInject constructor(private val noteRepository: NoteRepository):ViewModel(){


    suspend fun delete(note: Note){
        noteRepository.delete(note)
    }

    fun getAllNotes():LiveData<List<Note>>{
        return noteRepository.getAllNotes()
    }
}