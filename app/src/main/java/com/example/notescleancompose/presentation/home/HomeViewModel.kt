package com.example.notescleancompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescleancompose.common.ScreenViewState
import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.domain.usecases.DeleteNoteUseCase
import com.example.notescleancompose.domain.usecases.GetAllNotesUseCase
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
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNewsUseCase: UpdateNewsUseCase,
): ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
    init {
        getAllNotes()
    }

    private fun getAllNotes() {
//        getAllNotesUseCase.invoke()
        getAllNotesUseCase()
            .onEach {
                _state.value = HomeState(notes = ScreenViewState.Success(it))
            }
            .catch {
                _state.value = HomeState(notes = ScreenViewState.Error(it.message))
            }
            .launchIn(viewModelScope)
    }

    fun deleteNote(id: Long) = viewModelScope.launch {
        deleteNoteUseCase(id)
    }

    fun onBookmarkChange(note: Note) = viewModelScope.launch {
        updateNewsUseCase(note.copy(isBookmarked = !note.isBookmarked))
    }

}