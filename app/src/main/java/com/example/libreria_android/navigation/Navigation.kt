package com.example.libreria_android.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libreria_android.BookViewModel
import com.example.libreria_android.DB.AppContainer
import com.example.libreria_android.R
import com.example.libreria_android.Screens.UserFavouritesScreen
import com.example.libreria_android.Screens.UserListScreen
import com.example.libreria_android.books.BookList
import com.example.libreria_android.books.BookStatus
import com.example.libreria_android.books.Books
import com.example.libreria_android.books.toggleFavorite
import com.example.libreria_android.books.updateBookStatus


@Composable
fun Navigation(appContainer: AppContainer) {
    val viewModel = remember { BookViewModel(appContainer.provideBooksRepository()) }
    val mainController = rememberNavController()
    val navBackStack by mainController.currentBackStackEntryAsState()
    //TODO hacer peticiones a la api quitando lo de abajo

    val sampleBooks = remember {
        mutableStateListOf(
            Books(
                "1",
                "Cien años de soledad",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.cienanos
            )
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            MainNavigationBar(mainController, navBackStack)
        }, containerColor = Color(57, 86, 125, 255)

    ) { innerPadding ->

        NavHost(
            navController = mainController,
            startDestination = "principal",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding)

        ) {
            composable("principal") {
                BookList(
                    books = sampleBooks,
                    modifier = Modifier.padding(innerPadding),
                    onStatusChange = { bookId, newStatus ->
                        updateBookStatus(
                            sampleBooks,
                            bookId,
                            newStatus
                        )
                    },
                    onToggleFavorite = { bookId -> toggleFavorite(sampleBooks, bookId) },
                    viewModel = viewModel

                )
            }
            composable("pendientes") {
                UserListScreen(
                    modifier = Modifier.padding(innerPadding), sampleBooks,
                )
            }
            composable("favoritos") {
                UserFavouritesScreen(
                    modifier = Modifier.padding(innerPadding), sampleBooks
                )
            }
        }
    }
}
