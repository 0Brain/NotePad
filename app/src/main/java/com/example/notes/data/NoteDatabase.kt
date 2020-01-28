package com.example.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao():NoteDao

    companion object{
        private var instance:NoteDatabase?=null

        fun getInstance(context: Context):NoteDatabase?{
            if (instance == null){
                synchronized(NoteDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database")
                        .fallbackToDestructiveMigration().
                            addCallback(roomCallback).
                            build()
                }
            }

            return instance
        }
        private val roomCallback = object :RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}