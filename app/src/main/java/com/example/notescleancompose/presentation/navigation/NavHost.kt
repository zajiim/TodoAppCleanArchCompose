package com.example.notescleancompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notescleancompose.common.utils.navigateToSingleTop
import com.example.notescleancompose.presentation.bookmark.BookmarkScreen
import com.example.notescleancompose.presentation.bookmark.BookmarkViewModel
import com.example.notescleancompose.presentation.detail.DetailAssistedFactory
import com.example.notescleancompose.presentation.detail.DetailsScreen
import com.example.notescleancompose.presentation.home.HomeScreen
import com.example.notescleancompose.presentation.home.HomeViewModel


@Composable
fun NoteNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    bookmarkViewModel: BookmarkViewModel,
    assistedFactory: DetailAssistedFactory
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            val state by homeViewModel.state.collectAsState()
            HomeScreen(
                state = state,
                onBookmarkChange = homeViewModel::onBookmarkChange,
                onDelete = homeViewModel::deleteNote,
                onNoteClicked = {
                    navController.navigateToSingleTop(
                        route = "${Routes.Details.route}?id=$it"
                    )
                }
            )
        }

        composable(Routes.Bookmark.route) {
            val state by bookmarkViewModel.state.collectAsState()
            BookmarkScreen(
                state = state,
                modifier = modifier,
                onBookmarkChange = bookmarkViewModel::onBookmarkChange,
                onDelete =bookmarkViewModel::deleteNote,
                onNoteClicked = {
                    navController.navigateToSingleTop(
                        route = "${Routes.Details.route}?id=$it"
                    )
                }
            )
        }

        composable("${Routes.Details.route}?id={id}",
            arguments = listOf(navArgument("id") {
                NavType.LongType
                defaultValue = -1L
            })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailsScreen(
                modifier = modifier,
                noteId = id,
                assistedFactory = assistedFactory,
                navigateUp = { navController.navigateUp() }
            )
        }
    }

}

