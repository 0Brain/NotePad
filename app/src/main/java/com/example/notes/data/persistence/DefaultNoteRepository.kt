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

    override suspend fun insert(note: Note) {
        coroutineScope {
            launch {
                notesLocalDataSource.insertNote(note)
            }
        }
    }

    override suspend fun update(note: Note) {
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

    override fun getAllNotes(): LiveData<List<Note>> {
        return notesLocalDataSource.getAllNotes()
    }

}