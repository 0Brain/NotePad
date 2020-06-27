package com.example.notes.presentation.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.data.model.Note
import com.example.notes.data.repository.NoteRepository

class NoteViewModel (application: Application):AndroidViewModel(application){
    private var repository: NoteRepository =
        NoteRepository(application)

    private var allNotes:LiveData<List<Note>> = repository.getAllNotes()

    suspend fun update(note: Note){
        repository.update(note)
    }
    suspend fun delete(note: Note){
        repository.delete(note)
    }

    fun getAllNotes():LiveData<List<Note>>{
        return allNotes
    }
}