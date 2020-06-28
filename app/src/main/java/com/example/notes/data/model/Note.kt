package com.example.notes.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "note_table")
data class Note(
                @ColumnInfo(name = "note_name") var noteName:String = "",
                @ColumnInfo(name = "note_description") var noteDes:String = "",
                @NonNull @PrimaryKey @ColumnInfo(name = "note_id") var id: String = UUID.randomUUID().toString()
)