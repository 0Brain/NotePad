package com.example.notes.data.repository

import android.app.Application
import android.os.AsyncTask
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
        )!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun update(note: Note) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(
            noteDao
        ).execute(note)
    }


    fun delete(note: Note) {
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(
            noteDao
        ).execute(note)
    }


    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
    companion object{
        private class InsertNoteAsyncTask(val noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            override fun doInBackground(vararg p0: Note?) {
                noteDao.insertNote(p0[0]!!)
            }

        }
        private class UpdateNoteAsyncTask(val noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            override fun doInBackground(vararg p0: Note?) {
                noteDao.updateNote(p0[0]!!)
            }

        }
        private class DeleteNoteAsyncTask(val noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            override fun doInBackground(vararg p0: Note?) {
                noteDao.deleteNote(p0[0]!!)
            }

        }
    }
}