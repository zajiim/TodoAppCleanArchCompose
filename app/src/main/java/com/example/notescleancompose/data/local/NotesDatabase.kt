package com.example.notescleancompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notescleancompose.data.local.converters.DateConverter
import com.example.notescleancompose.data.local.model.Note

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase: RoomDatabase() {
    abstract val notesDao: NotesDao
}