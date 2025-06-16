package com.example.lurubooks

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
import com.example.lurubooks.db.AppContainer
import com.example.lurubooks.screens.LogInScreen
import com.example.lurubooks.navigation.Navigation
import com.example.lurubooks.ui.theme.lurubooksTheme
import com.example.lurubooks.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    val appContainer by lazy { AppContainer(context = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            lurubooksTheme {
                val userViewModel = remember { UserViewModel(appContainer.provideUserRepository()) }
                val mainController = rememberNavController()
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
