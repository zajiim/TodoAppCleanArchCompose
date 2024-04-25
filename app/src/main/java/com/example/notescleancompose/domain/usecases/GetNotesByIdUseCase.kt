package com.example.notescleancompose.domain.usecases

import com.example.notescleancompose.domain.repository.Repository
import javax.inject.Inject

class GetNotesByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Long) = repository.getNoteById(id)
}