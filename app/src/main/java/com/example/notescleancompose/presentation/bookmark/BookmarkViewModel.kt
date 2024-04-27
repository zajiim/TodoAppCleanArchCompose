package com.example.notescleancompose.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescleancompose.common.ScreenViewState
import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.usecases.DeleteNoteUseCase
import com.example.notescleancompose.domain.usecases.FilteredBookmarkUseCase
import com.example.notescleancompose.domain.usecases.UpdateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val updateNewsUseCase: UpdateNewsUseCase,
    private val filteredBookmarkUseCase: FilteredBookmarkUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        getBookmarkedNotes()
    }

    private fun getBookmarkedNotes() {
        filteredBookmarkUseCase()
            .onEach {
                _state.value = BookmarkState(
                    notes = ScreenViewState.Success(it)
                )
            }.catch {
                _state.value = BookmarkState(
                    notes = ScreenViewState.Error(it.message)
                )
            }.launchIn(viewModelScope)
    }

    fun onBookmarkChange(note: Note) {
        viewModelScope.launch {
            updateNewsUseCase(note.copy(
                isBookmarked = !note.isBookmarked
            ))
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(id)
        }
    }

}