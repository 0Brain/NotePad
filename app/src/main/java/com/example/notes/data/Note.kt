package com.example.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")
data class Note(
                var noteName:String,
                var noteDes:String,
                var priority: Int
){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    fun Text1():String{
        return this.noteName
    }
}