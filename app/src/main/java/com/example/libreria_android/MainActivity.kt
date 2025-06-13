package com.example.libreria_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libreria_android.Screens.LogInScreen
import com.example.libreria_android.navigation.Navigation
import com.example.libreria_android.ui.theme.libreria_androidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            libreria_androidTheme {
                val mainController = rememberNavController()
                val navBackStack by mainController.currentBackStackEntryAsState()
                NavHost(
                    navController = mainController,
                    startDestination = "login",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable("login") {
                        LogInScreen(navController = mainController)
                    }
                    composable("principal") {
                        Navigation()
                    }
                }
            }
        }
    }
}
