package com.example.notes.persistence.local

import androidx.lifecycle.LiveData
import com.example.notes.model.Note
import com.example.notes.persistence.source.NotesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesLocalDataSource internal constructor(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    :NotesDataSource {

    override suspend fun insertNote(note: Note) = withContext(ioDispatcher){
      noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) = withContext(ioDispatcher){
        noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) = withContext(ioDispatcher) {
        noteDao.updateNote(note)
    }

    override fun getNoteById(noteId: String): LiveData<Note> {
        return noteDao.loadNoteById(noteId)
    }

    override fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }
}