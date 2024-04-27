package com.example.notescleancompose.presentation.bookmark

import com.example.notescleancompose.common.ScreenViewState
import com.example.notescleancompose.data.local.model.Note

data class BookmarkState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
)
