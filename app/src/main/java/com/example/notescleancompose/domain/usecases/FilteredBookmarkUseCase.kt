package com.example.notescleancompose.domain.usecases

import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredBookmarkUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getBookmarkedNotes()
}