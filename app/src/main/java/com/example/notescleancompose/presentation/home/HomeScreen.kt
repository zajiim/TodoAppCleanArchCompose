package com.example.notescleancompose.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onBookmarkChange: (note: Note) -> Unit,
    onDelete: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {

    when(state.notes) {
        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Unknown error",
                color = MaterialTheme.colorScheme.error
            )
        }
        ScreenViewState.Loading -> { 
            CircularProgressIndicator()
        }
        is ScreenViewState.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onBookmarkChange = onBookmarkChange,
                onDelete = onDelete,
                onNoteClicked = onNoteClicked
            ) 
        }
    }
}


@Composable
private fun HomeDetail(
    notes: List<Note>,
    modifier: Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDelete: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
        itemsIndexed(notes) { index, noteItem ->
            NoteCard(
                index = index,
                note =noteItem,
                onBookmarkChange = onBookmarkChange,
                onDelete = onDelete,
                onNoteClicked = onNoteClicked
            ) 
        }
    }
}