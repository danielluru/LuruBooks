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
                1,
                "Cien años de soledad",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.cienanos
            ), Books(
                2,
                "Don Quijote de la Mancha",
                "Miguel de Cervantes",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.quijote
            ), Books(
                3,
                "La sombra del viento",
                "Carlos Ruiz Zafón",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.lasombradelviento
            ), Books(
                4,
                "El amor en los tiempos del cólera",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.elamorenlostiemposdelcolera
            ), Books(
                5,
                "Crónica de una muerte anunciada",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.cronicadeunamuerteanunciada
            ), Books(
                6,
                "La casa de los espíritus",
                "Isabel Allende",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.lacasadelosespiritus
            ), Books(
                7,
                "Rayuela",
                "Julio Cortázar",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.rayuela
            ), Books(
                8,
                "El túnel",
                "Ernesto Sabato",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.eltunel
            ), Books(
                9,
                "Ficciones",
                "Jorge Luis Borges",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.ficciones
            ), Books(
                10,
                "Pedro Páramo",
                "Juan Rulfo",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.pedroparamo
            ), Books(
                11,
                "La ciudad y los perros",
                "Mario Vargas Llosa",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.laciudadylosperros
            ), Books(
                12,
                "El Aleph",
                "Jorge Luis Borges",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.elaleph
            ), Books(
                13,
                "Los detectives salvajes",
                "Roberto Bolaño",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.losdetectivessalvajes
            ), Books(
                14,
                "La fiesta del chivo",
                "Mario Vargas Llosa",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.lafiestadelchivo
            ), Books(
                15,
                "El otoño del patriarca",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.elotonodelpatriarca
            ), Books(
                16,
                "Sobre héroes y tumbas",
                "Ernesto Sabato",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.sobreheroesytumbas
            ), Books(
                17,
                "La tregua",
                "Mario Benedetti",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.latregua
            ), Books(
                18,
                "El coronel no tiene quien le escriba",
                "Gabriel García Márquez",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.elcoronelnotienequienleescriba
            ), Books(
                19,
                "La colmena",
                "Camilo José Cela",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.lacolmena
            ), Books(
                20,
                "Aura",
                "Carlos Fuentes",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.aura
            ), Books(
                21,
                "Mi lucha",
                "Adolf Hitler",
                BookStatus.NO_GUARDADO,
                false,
                R.drawable.milucha
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
                    modifier = Modifier.padding(innerPadding), sampleBooks
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
