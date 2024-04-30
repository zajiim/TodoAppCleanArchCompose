package com.example.notescleancompose.presentation.navigation

sealed class Routes(val route: String) {
    data object Home: Routes("home")
    data object Bookmark: Routes("bookmark")
    data object Details: Routes("details")
}
