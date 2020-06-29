package com.example.notes.data.persistence

import androidx.lifecycle.LiveData
import com.example.notes.data.model.Note
import com.example.notes.data.persistence.local.NotesLocalDataSource
import com.example.notes.data.persistence.source.NotesDataSource
import com.example.notes.data.repository.NoteRepository
import kotlinx.coroutines.*

class DefaultNoteRepository (
    private val notesLocalDataSource: NotesDataSource,
    private val ioDispatcher:CoroutineDispatcher = Dispatchers.IO)
    :NoteRepository{

    override suspend fun insertNote(note: Note) {
        coroutineScope {
            launch {
                notesLocalDataSource.insertNote(note)
            }
        }
    }

    override suspend fun updateNote(note: Note) {
        coroutineScope {
            launch {
                notesLocalDataSource.updateNote(note)
            }
        }
    }

    override suspend fun delete(note: Note) {
        coroutineScope {
            launch {
                notesLocalDataSource.deleteNote(note)
            }
        }
    }

    override fun getNoteById(noteId: String): LiveData<Note> {
        return notesLocalDataSource.getNoteById(noteId)

    }

    override fun getAllNotes(): LiveData<List<Note>> {
        return notesLocalDataSource.getAllNotes()
    }

}