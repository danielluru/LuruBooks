package com.example.lurubooks.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
fun MainNavigationBar(
    mainController: NavHostController, navBackStack: NavBackStackEntry?
) {
    NavigationBar(
        tonalElevation = 25.dp,
        containerColor = Color(57, 85, 124, 255),
        contentColor = Color(205, 184, 164, 255)
    ) {
        items.forEach { item ->
            val isSelected = item.title.lowercase() == navBackStack?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                colors =  NavigationBarItemDefaults.colors().copy(
                    selectedIndicatorColor = Color(205, 184, 164, 255),
                ),
                icon = {
                    val tint by animateColorAsState(
                        if (isSelected) Color.White
                        else Color(205, 184, 164, 255), label = ""
                    )

                    Icon(
                        imageVector = item.icon, contentDescription = item.title, tint = tint
                    )
                },
                onClick = {
                    mainController.navigate(item.title.lowercase()) {
                        popUpTo(mainController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

data class BottomNavigation(
    val title: String, val icon: ImageVector
)

val items = listOf(
    BottomNavigation("Principal", Icons.Default.Home),
    BottomNavigation("Pendientes", Icons.Default.List),
    BottomNavigation("Favoritos", Icons.Default.Favorite)
)