package com.example.libreria_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libreria_android.db.AppContainer
import com.example.libreria_android.screens.LogInScreen
import com.example.libreria_android.navigation.Navigation
import com.example.libreria_android.ui.theme.libreria_androidTheme
import com.example.libreria_android.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    val appContainer by lazy { AppContainer(context = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = appContainer.provideUserRepository()
        val booksRepository = appContainer.provideBooksRepository()
        enableEdgeToEdge()
        setContent {
            libreria_androidTheme {
                val userViewModel = remember { UserViewModel(appContainer.provideUserRepository()) }
                val mainController = rememberNavController()
                val navBackStack by mainController.currentBackStackEntryAsState()
                NavHost(
                    navController = mainController,
                    startDestination = "login",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable("login") {
                        LogInScreen(navController = mainController, userViewModel = userViewModel)
                    }
                    composable("principal") {
                        Navigation(appContainer)

                    }
                }
            }
        }
    }
}
