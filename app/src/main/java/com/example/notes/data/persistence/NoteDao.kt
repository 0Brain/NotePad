package com.example.notes.data.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.model.Note


@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table WHERE note_id=:noteId")
    fun loadNoteById(noteId:String):LiveData<Note>

    @Query("SELECT * FROM note_table")
    fun getAllNotes():LiveData<List<Note>>
}