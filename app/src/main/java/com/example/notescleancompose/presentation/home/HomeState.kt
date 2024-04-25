package com.example.notescleancompose.presentation.home

import com.example.notescleancompose.common.ScreenViewState
import com.example.notescleancompose.data.local.model.Note
data class HomeState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading
)