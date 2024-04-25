package com.example.notescleancompose.domain.usecases

import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.repository.Repository
import javax.inject.Inject

class UpdateNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.update(note)
}