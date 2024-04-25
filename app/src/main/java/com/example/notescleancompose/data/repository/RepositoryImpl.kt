package com.example.notescleancompose.data.repository

import com.example.notescleancompose.data.local.NotesDao
import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
): Repository {
    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override fun getNoteById(id: Long): Flow<Note> {
        return notesDao.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        notesDao.insertNote(note)
    }

    override suspend fun update(note: Note) {
        notesDao.updateNote(note)
    }

    override suspend fun delete(id: Long) {
        notesDao.deleteNote(id)
    }

    override fun getBookmarkedNotes(): Flow<List<Note>> {
        return notesDao.getBookmarkedNotes()
    }
}