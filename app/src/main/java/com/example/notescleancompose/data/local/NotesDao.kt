package com.example.notescleancompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notescleancompose.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY createdDate")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id=:id ORDER BY createdDate")
    fun getNoteById(id: Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes WHERE id= :id")
    suspend fun deleteNote(id: Long)

    @Query("SELECT * FROM notes WHERE isBookmarked = 1 ORDER BY createdDate DESC")
    fun getBookmarkedNotes(): Flow<List<Note>>
}