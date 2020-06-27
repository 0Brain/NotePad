package com.example.notes.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.notes.data.model.Note
import com.example.notes.data.persistence.NoteDao
import com.example.notes.data.persistence.NoteDatabase

class NoteRepository (application: Application) {
    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(
            application.applicationContext
        )
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun loadNoteById(note:String):LiveData<Note>{
        return noteDao.loadNoteById(note)
    }

    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun update(note: Note) {
       noteDao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}