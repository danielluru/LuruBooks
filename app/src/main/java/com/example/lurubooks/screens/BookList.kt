package com.example.lurubooks.screens

import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.lurubooks.api.BooksAPIClient
import com.example.lurubooks.viewModels.BookViewModel
import com.example.lurubooks.R
import com.example.lurubooks.books.BookStatus
import com.example.lurubooks.books.Books
import kotlinx.coroutines.launch


@Composable
fun BookList(
    modifier: Modifier = Modifier, viewModel: BookViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var libros = remember { mutableStateListOf<Books>() }
    var pageNumber by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        try {
            libros.clear()
            val apiClient = BooksAPIClient.apiService
            val response = apiClient.getTrendingBooks()
            val rawBody = response.errorBody()?.string() ?: response.body().toString()
            Log.d("API_RAW", rawBody)
            Log.d("API_AAAAAA", response.toString())
            if (response.isSuccessful) {
                Log.d("API_RESULT", response.body().toString())
                response.body()?.works?.forEach { doc ->
                    val id = doc.key ?: "aaa"
                    val title = doc.title ?: "Título desconocido"
                    val author = doc.author_name?.joinToString(", ") ?: "Autor desconocido"
                    val coverUrl =
                        doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                    libros.add(
                        Books(
                            id, title, author, BookStatus.NO_GUARDADO, false, coverUrl
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("API_RESULT", "Exception: ${e.message}")
        }
    }

    val dbBooks by viewModel.books.collectAsState()
    val dbBooksMap = dbBooks.associateBy { it.id }

    Column(
        modifier = Modifier.background(Color(205, 184, 164, 255))
    ) {
        Text(
            "Biblioteca",
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(57, 85, 124, 255))
                .padding(5.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.background(Color(34, 46, 79, 255)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Encuentra tus libros favoritos",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                    Text(
                        text = "¡Mas de 100.000 libros!",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Image(
                        painter = painterResource(R.drawable.hero),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .height(300.dp),
                        contentScale = ContentScale.FillHeight
                    )
                    Row(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Buscar libro...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(253, 253, 253, 255),
                                unfocusedContainerColor = Color(253, 253, 253, 255),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            shape = RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        )
                        Button(
                            onClick = {
                                // filtrar la lista de libros según el texto de búsqueda
                                coroutineScope.launch {
                                    try {
                                        libros.clear() // Limpiar la lista antes de buscar
                                        val apiClient = BooksAPIClient.apiService
                                        val response = apiClient.searchBooks(searchText)
                                        if (response.isSuccessful) {
                                            Log.d("API_RESULT", response.body().toString())
                                            response.body()?.docs?.forEach { doc ->
                                                val id = doc.key
                                                val title = doc.title ?: "Título desconocido"
                                                val author = doc.author_name?.joinToString(", ")
                                                    ?: "Autor desconocido"
                                                val ratingsCount = doc.ratings_count ?: 0
                                                val coverUrl =
                                                    doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                                                Log.d("API_RESULT", "Cover URL: $coverUrl")
                                                Log.d(
                                                    "API_RESULT",
                                                    "Título: $title, Autor: $author, Ratings: $ratingsCount"
                                                )
                                                libros.add(
                                                    Books(
                                                        id, // Asigna un ID único si es necesario
                                                        title,
                                                        author,
                                                        BookStatus.NO_GUARDADO,
                                                        false,
                                                        coverUrl
                                                    )
                                                )
                                            }
                                        } else {
                                            Log.d("API_RESULT", "Error: ${response.code()}")
                                        }
                                    } catch (e: Exception) {
                                        Log.d("API_RESULT", "Exception: ${e.message}")
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = Color(203, 183, 163, 255),
                            ),
                            shape = RoundedCornerShape(0.dp, 15.dp, 15.dp, 0.dp),
                            modifier = Modifier.height(56.dp)
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Buscar", tint = Color.White
                            )
                        }
                    }
                }

            }
            if (libros.isEmpty()) {
                item {
                    val animatedProgress =
                        remember { androidx.compose.animation.core.Animatable(0f) }
                    LaunchedEffect(Unit) {
                        animatedProgress.animateTo(
                            targetValue = 1f,
                            animationSpec = androidx.compose.animation.core.tween(durationMillis = 5000)
                        )
                    }
                    CircularProgressIndicator(
                        progress = {
                            animatedProgress.value
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 50.dp),
                    )
                }
            }
            items(libros.size) { index ->
                val book = remember { dbBooksMap[libros[index].id] ?: libros[index] }
                Column(
                    modifier = Modifier
                        .padding(40.dp, 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shadow(6.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(34, 46, 80, 255))
                        .defaultMinSize(minHeight = 520.dp)
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = book.coverUrl ?: R.drawable.noimage,
                        contentDescription = "Portada del libro",
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillHeight
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Log.d("BookList", "Book cover URL: ${book.coverUrl}")
                        Spacer(modifier = Modifier.size(10.dp))
                        Column {
                            val title = if (book.title.length > 50) book.title.substring(0, minOf(book.title.length, 50)) + "..." else book.title
                            Text(
                                text = title,
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .fillMaxWidth()
                            )
                            val author = book.author.split(",").take(2).joinToString(", ")
                            Text(
                                text = author,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontStyle = FontStyle.Italic,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            var status by remember { mutableStateOf(book.status) }
                            Spacer(modifier = Modifier.size(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        status = BookStatus.PENDIENTE
                                        viewModel.changeBookStatus(book, BookStatus.PENDIENTE)
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors().copy(
                                        containerColor = if (status == BookStatus.PENDIENTE)
                                            Color(240, 240, 240, 255) else Color(57, 86, 125, 255)
                                    )
                                ) {
                                    Icon(
                                        contentDescription = "Pendiente",
                                        painter = painterResource(id = R.drawable.pendiente),
                                        tint = if (status == BookStatus.PENDIENTE) Color(
                                            4, 33, 71, 255
                                        ) else Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.size(20.dp))
                                Button(
                                    onClick = {
                                        status = BookStatus.LEYENDO
                                        viewModel.changeBookStatus(book, BookStatus.LEYENDO)
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors().copy(
                                        containerColor = if (status == BookStatus.LEYENDO) Color(
                                            240,
                                            240,
                                            240,
                                            255
                                        ) else Color(57, 86, 125, 255)
                                    )
                                ) {
                                    Icon(
                                        contentDescription = "Leyendo",
                                        painter = painterResource(id = R.drawable.leyendo),
                                        tint = if (status == BookStatus.LEYENDO) Color(
                                            4, 33, 71, 255
                                        ) else Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.size(20.dp))
                                Button(
                                    onClick = {
                                        status = BookStatus.TERMINADO
                                        viewModel.changeBookStatus(book, BookStatus.TERMINADO)
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors().copy(
                                        containerColor = if (status == BookStatus.TERMINADO) Color(
                                            240,
                                            240,
                                            240,
                                            255
                                        ) else Color(57, 86, 125, 255)
                                    )
                                ) {
                                    Icon(
                                        contentDescription = "Terminado",
                                        painter = painterResource(id = R.drawable.terminado),
                                        tint = if (status == BookStatus.TERMINADO) Color(
                                            4, 33, 71, 255
                                        ) else Color.White,
                                    )
                                }
                            }


                            var favorite by remember { mutableStateOf(book.isFavorite) }
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.toggleFavorite(book)
                                        favorite = !favorite
                                    }
                                }, colors = ButtonDefaults.buttonColors().copy(
                                    containerColor = Color(210, 1, 112, 255),
                                ), modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    imageVector = if (favorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    modifier = Modifier.size(24.dp),
                                )
                            }

                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 20.dp),
                ) {
                    if (!libros.isEmpty()) {
                        Button(
                            onClick = {
                                if (pageNumber > 1) {
                                    pageNumber--
                                    coroutineScope.launch {
                                        libros.clear()
                                        val apiClient = BooksAPIClient.apiService
                                        if (searchText.isEmpty()) {
                                            val response =
                                                apiClient.getTrendingBooks(page = pageNumber)
                                            if (response.isSuccessful) {
                                                Log.d("API_RESULT", response.body().toString())
                                                response.body()?.works?.forEach { doc ->
                                                    val id = doc.key ?: "aaa"
                                                    val title = doc.title ?: "Título desconocido"
                                                    val author = doc.author_name?.joinToString(", ")
                                                        ?: "Autor desconocido"
                                                    val coverUrl =
                                                        doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                                                    libros.add(
                                                        Books(
                                                            id,
                                                            title,
                                                            author,
                                                            BookStatus.NO_GUARDADO,
                                                            false,
                                                            coverUrl
                                                        )
                                                    )
                                                }
                                            }
                                        } else {
                                            libros.clear() // Limpiar la lista antes de buscar
                                            val apiClient = BooksAPIClient.apiService
                                            val response =
                                                apiClient.searchBooks(searchText, page = pageNumber)
                                            if (response.isSuccessful) {
                                                Log.d("API_RESULT", response.body().toString())
                                                response.body()?.docs?.forEach { doc ->
                                                    val id = doc.key
                                                    val title = doc.title ?: "Título desconocido"
                                                    val author = doc.author_name?.joinToString(", ")
                                                        ?: "Autor desconocido"
                                                    val ratingsCount = doc.ratings_count ?: 0
                                                    val coverUrl =
                                                        doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                                                    Log.d("API_RESULT", "Cover URL: $coverUrl")
                                                    Log.d(
                                                        "API_RESULT",
                                                        "Título: $title, Autor: $author, Ratings: $ratingsCount"
                                                    )
                                                    libros.add(
                                                        Books(
                                                            id, // Asigna un ID único si es necesario
                                                            title,
                                                            author,
                                                            BookStatus.NO_GUARDADO,
                                                            false,
                                                            coverUrl
                                                        )
                                                    )
                                                }
                                            } else {
                                                Log.d("API_RESULT", "Error: ${response.code()}")
                                            }
                                        }

                                    }
                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Text("<")

                        }
                        Text(
                            text = "Pg $pageNumber",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterVertically),
                            color = Color(57, 84, 123, 255),
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = {
                                pageNumber++
                                coroutineScope.launch {
                                    libros.clear()
                                    val apiClient = BooksAPIClient.apiService
                                    if (searchText.isEmpty()) {
                                        val response = apiClient.getTrendingBooks(page = pageNumber)
                                        if (response.isSuccessful) {
                                            Log.d("API_RESULT", response.body().toString())
                                            response.body()?.works?.forEach { doc ->
                                                val id = doc.key ?: "aaa"
                                                val title = doc.title ?: "Título desconocido"
                                                val author = doc.author_name?.joinToString(", ")
                                                    ?: "Autor desconocido"
                                                val coverUrl =
                                                    doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                                                libros.add(
                                                    Books(
                                                        id,
                                                        title,
                                                        author,
                                                        BookStatus.NO_GUARDADO,
                                                        false,
                                                        coverUrl
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        libros.clear() // Limpiar la lista antes de buscar
                                        val apiClient = BooksAPIClient.apiService
                                        val response =
                                            apiClient.searchBooks(searchText, page = pageNumber)
                                        if (response.isSuccessful) {
                                            Log.d("API_RESULT", response.body().toString())
                                            response.body()?.docs?.forEach { doc ->
                                                val id = doc.key
                                                val title = doc.title ?: "Título desconocido"
                                                val author = doc.author_name?.joinToString(", ")
                                                    ?: "Autor desconocido"
                                                val ratingsCount = doc.ratings_count ?: 0
                                                val coverUrl =
                                                    doc.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                                                Log.d("API_RESULT", "Cover URL: $coverUrl")
                                                Log.d(
                                                    "API_RESULT",
                                                    "Título: $title, Autor: $author, Ratings: $ratingsCount"
                                                )
                                                libros.add(
                                                    Books(
                                                        id, // Asigna un ID único si es necesario
                                                        title,
                                                        author,
                                                        BookStatus.NO_GUARDADO,
                                                        false,
                                                        coverUrl
                                                    )
                                                )
                                            }
                                        } else {
                                            Log.d("API_RESULT", "Error: ${response.code()}")
                                        }
                                    }

                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Text(">")
                        }
                    }
                }
            }
        }
    }
}