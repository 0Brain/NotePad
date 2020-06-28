package com.example.notes.data.persistence.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.data.model.Note
import com.example.notes.data.persistence.local.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}