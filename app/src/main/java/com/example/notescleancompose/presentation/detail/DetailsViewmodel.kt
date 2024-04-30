package com.example.notescleancompose.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.usecases.AddNoteUseCase
import com.example.notescleancompose.domain.usecases.GetNotesByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailsViewmodel @AssistedInject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesByIdUseCase: GetNotesByIdUseCase,
    @Assisted private val noteId: Long
): ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    val isFormNotBlank: Boolean
        get() = state.title.isNotEmpty() &&
                state.content.isNotEmpty()

    private val note: Note
        get() = state.run {
            Note(
                id = id,
                title = title,
                content = content,
                createdDate = createdDate,
                isBookmarked = isBookmarked
            )
        }

    init {
        initialize()
    }

    private fun initialize() {
        val isUpdating = noteId != -1L
        state = state.copy(
            isUpdatingNote = isUpdating
        )
        if (isUpdating) {
            getNoteById()
        }
    }

    private fun getNoteById() = viewModelScope.launch {
        getNotesByIdUseCase(noteId).collectLatest { note ->
            state = state.copy(
                id = note.id,
                title = note.title,
                content = note.content,
                createdDate = note.createdDate,
                isBookmarked = note.isBookmarked
            )
        }
    }

    fun onTitleChange(title: String) {
        state = state.copy(
            title = title
        )
    }
    fun onContentChange(content: String) {
        state = state.copy(
            content = content
        )
    }

    fun onBookmarkChange(isBookmarked: Boolean) {
        state = state.copy(
            isBookmarked = isBookmarked
        )
    }
    fun addOrUpdateNote() = viewModelScope.launch {
        addNoteUseCase(note = note)
    }


}
@Suppress("UNCHECKED_CAST")
class DetailedViewModelFactory(
    private val noteId: Long,
    private val assistedFactory: DetailAssistedFactory
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(noteId) as T
    }
}

@AssistedFactory
interface DetailAssistedFactory {
    fun create(noteId: Long): DetailsViewmodel
}