package com.example.notescleancompose.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notescleancompose.common.ScreenViewState
import com.example.notescleancompose.data.local.model.Note
import com.example.notescleancompose.presentation.home.components.NoteCard

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    modifier: Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDelete: (id: Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    when(state.notes) {
        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Unknown error",
                color = MaterialTheme.colorScheme.error
            )
        }
        ScreenViewState.Loading -> CircularProgressIndicator()
        is ScreenViewState.Success -> {
            val notes = state.notes.data
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(4.dp)
            ) {
                itemsIndexed(notes) {index, noteItem ->
                    NoteCard(
                        index = index,
                        note = noteItem,
                        onBookmarkChange = onBookmarkChange,
                        onDelete = onDelete,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }
    }
}