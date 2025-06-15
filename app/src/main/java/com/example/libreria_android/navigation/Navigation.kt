package com.example.libreria_android.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libreria_android.db.AppContainer
import com.example.libreria_android.screens.BookList
import com.example.libreria_android.screens.UserFavouritesScreen
import com.example.libreria_android.screens.UserListScreen
import com.example.libreria_android.viewModels.BookViewModel


@Composable
fun Navigation(appContainer: AppContainer) {
    val viewModel = remember { BookViewModel(appContainer.provideBooksRepository()) }
    val mainController = rememberNavController()
    val navBackStack by mainController.currentBackStackEntryAsState()
    //TODO hacer peticiones a la api quitando lo de abajo

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
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )
            }
            composable("pendientes") {
                UserListScreen(
                    modifier = Modifier.padding(innerPadding), viewModel
                )
            }
            composable("favoritos") {
                UserFavouritesScreen(
                    modifier = Modifier.padding(innerPadding), viewModel
                )
            }
        }
    }
}