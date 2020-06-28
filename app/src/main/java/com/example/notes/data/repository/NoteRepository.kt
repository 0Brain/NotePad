package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.model.Note
import javax.inject.Singleton

@Singleton
interface NoteRepository{

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    fun getAllNotes(): LiveData<List<Note>>
}