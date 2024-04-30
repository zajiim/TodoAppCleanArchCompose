package com.example.notescleancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.notescleancompose.common.utils.navigateToSingleTop
import com.example.notescleancompose.presentation.bookmark.BookmarkViewModel
import com.example.notescleancompose.presentation.detail.DetailAssistedFactory
import com.example.notescleancompose.presentation.home.HomeViewModel
import com.example.notescleancompose.presentation.navigation.NoteNavGraph
import com.example.notescleancompose.presentation.navigation.Routes
import com.example.notescleancompose.ui.theme.NotesCleanComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: DetailAssistedFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesCleanComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    NotesApp()
                }
            }
        }
    }


    @Composable
    fun NotesApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val bookmarkViewModel: BookmarkViewModel = viewModel()
        val navController = rememberNavController()
        var currentTab by remember {
            mutableStateOf(TabScreen.HOME)
        }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row(horizontalArrangement = Arrangement.Center) {
                            InputChip(
                                selected = currentTab == TabScreen.HOME,
                                onClick = {
                                    currentTab = TabScreen.HOME
                                    navController.navigateToSingleTop(
                                        route = Routes.Home.route
                                    )
                                },
                                label = {
                                    Text(text = "Home")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.size(12.dp))

                            InputChip(
                                selected = currentTab == TabScreen.BOOKMARK,
                                onClick = {
                                    currentTab = TabScreen.BOOKMARK
                                    navController.navigateToSingleTop(
                                        route = Routes.Bookmark.route
                                    )
                                },
                                label = {
                                    Text(text = "Bookmark")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = null
                                    )
                                }
                            )
                        }

                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigateToSingleTop(
                                route = Routes.Details.route
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            NoteNavGraph(
                modifier = Modifier.padding(it),
                navController = navController,
                homeViewModel = homeViewModel,
                bookmarkViewModel = bookmarkViewModel,
                assistedFactory = assistedFactory
            )
        }

    }

    enum class TabScreen {
        HOME,
        BOOKMARK
    }

}