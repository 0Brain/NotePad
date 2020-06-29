package com.example.notes.ui.addNote

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Note
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewNoteViewModel @ViewModelInject constructor(private val noteRepository: NoteRepository) : ViewModel(){

    fun getNote(noteId:String):LiveData<Note>{
        return noteRepository.getNoteById(noteId)
    }


    fun insertNote(note:Note){
        viewModelScope.launch {
            insert(note)
        }
    }

    private suspend fun insert(note: Note) = withContext(Dispatchers.IO){
        noteRepository.insertNote(note)
    }


    fun updateNote(note:Note){
        viewModelScope.launch {
            update(note)
        }
    }

    private suspend fun update(note: Note) = withContext(Dispatchers.IO){
        noteRepository.updateNote(note)
    }
}