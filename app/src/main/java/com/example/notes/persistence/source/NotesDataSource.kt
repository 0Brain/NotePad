package com.example.notes.persistence.source

import androidx.lifecycle.LiveData
import com.example.notes.model.Note

interface NotesDataSource {

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note:Note)

    fun getNoteById(noteId:String):LiveData<Note>

    fun getAllNotes():LiveData<List<Note>>
}