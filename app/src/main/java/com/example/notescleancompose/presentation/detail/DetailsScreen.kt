package com.example.notescleancompose.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailsScreen(
    modifier: Modifier,
    noteId: Long,
    assistedFactory: DetailAssistedFactory,
    navigateUp: () -> Unit,
) {
    val viewModel = viewModel(
        modelClass = DetailsViewmodel::class.java,
        factory = DetailedViewModelFactory(
            noteId = noteId,
            assistedFactory = assistedFactory
        )
    )

    val state = viewModel.state

    DetailsScreen(
        modifier = modifier,
        isUpdatingNote = state.isUpdatingNote,
        title = state.title,
        content = state.content,
        isBookmark = state.isBookmarked,
        onBookmarkChange = viewModel::onBookmarkChange,
        isFormNotBlank = state.isUpdatingNote,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onBtnClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigate = navigateUp
    )
}

@Composable
private fun DetailsScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookmark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBtnClick: () -> Unit,
    onNavigate: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TopSection(
            title = title,
            isBookmark = isBookmark,
            onBookmarkChange = onBookmarkChange,
            onTitleChange = onTitleChange,
            onNavigate = onNavigate
        )
        Spacer(modifier = Modifier.size(12.dp))
        AnimatedVisibility(visible = isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onBtnClick) {
                    val icon = if (isUpdatingNote) Icons.Default.Edit else Icons.Default.Check
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            onValueChange = onContentChange,
            placeHolder = "Content",
        )
    }

}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookmark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigate: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigate) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            onValueChange = onTitleChange,
            placeHolder = "Title",
            labelAlign = TextAlign.Center
        )
        IconButton(onClick = { onBookmarkChange(!isBookmark) }) {
            val icon = if (isBookmark) Icons.Default.Favorite else Icons.Default.FavoriteBorder
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }

    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    labelAlign: TextAlign? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $placeHolder",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}