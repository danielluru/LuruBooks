package com.example.libreria_android.books

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libreria_android.API.RetrofitClient
import com.example.libreria_android.R
import kotlinx.coroutines.launch


@Composable
fun BookList(
    modifier: Modifier = Modifier,
    books: List<Books>,
    onStatusChange: (Int, BookStatus) -> Unit,
    onToggleFavorite: (Int) -> Unit


) {
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var libros = remember {
        mutableStateListOf<Books>(
        Books(
            1,
            "Cien años de soledad",
            "Gabriel García Márquez",
            BookStatus.NO_GUARDADO,
            false,
            R.drawable.cienanos
        )

        )
    }
    Column {
        Text(
            "Biblioteca",
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar libro...") },
                modifier = Modifier
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(34, 46, 80, 255),
                    unfocusedContainerColor = Color(34, 46, 80, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {
                    // Aquí puedes implementar la lógica de búsqueda
                    // Por ejemplo, filtrar la lista de libros según el texto de búsqueda
                    coroutineScope.launch {
                        try {
                            val apiClient = RetrofitClient.apiService
                            val response = apiClient.searchBooks("El señor de los anillos")
                            if (response.isSuccessful) {
                                Log.d("API_RESULT", response.body().toString())
                                response.body()?.docs?.forEach { doc ->
                                    val title = doc.title ?: "Título desconocido"
                                    val author = doc.author_name?.joinToString(", ") ?: "Autor desconocido"
                                    val ratingsCount = doc.ratings_count ?: 0
                                    Log.d("API_RESULT", "Título: $title, Autor: $author, Ratings: $ratingsCount")
                                    libros.add(
                                        Books(
                                            0, // Asigna un ID único si es necesario
                                            title,
                                            author,
                                            BookStatus.NO_GUARDADO,
                                            false,
                                            R.drawable.cienanos // Cambia esto según tu lógica
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
                modifier = Modifier
            ) {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
            }
        }


        LazyColumn() {
            items(libros) { book ->
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .shadow(6.dp)
                        .background(
                            color = Color(34, 46, 80, 255), shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = book.image),
                        "",
                        modifier = Modifier
                            .height(240.dp)
                            .width(160.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column {
                        Text(
                            fontStyle = FontStyle.Italic,
                            text = book.title,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = book.author,
                            color = Color.White,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp
                        )
                        var status by remember { mutableStateOf(book.status) }
                        Row {
                            Button(
                                onClick = {
                                    onStatusChange(book.id, BookStatus.PENDIENTE)
                                    status = BookStatus.PENDIENTE
                                },
                                modifier = Modifier.fillMaxWidth(0.65f),
                                colors = ButtonDefaults.buttonColors().copy(
                                    containerColor = if (status == BookStatus.PENDIENTE) Color(
                                        4,
                                        33,
                                        71,
                                        255
                                    ) else Color(57, 86, 125, 255)
                                )
                            ) {
                                Text("Pendiente")
                            }

                            var favorite_image by remember { mutableStateOf(if (book.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder) }
                            Button(
                                onClick = {
                                    onToggleFavorite(book.id)
                                    favorite_image =
                                        if (book.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder
                                }, colors = ButtonDefaults.buttonColors().copy(
                                    containerColor = Color(210, 1, 112, 255),
                                )
                            ) {
                                Image(
                                    imageVector = favorite_image,
                                    contentDescription = "Favorito",
                                    modifier = Modifier.size(24.dp),
                                )
                            }

                        }
                        Row {
                            Button(
                                onClick = {
                                    onStatusChange(book.id, BookStatus.LEYENDO)
                                    status = BookStatus.LEYENDO
                                },
                                modifier = Modifier.fillMaxWidth(0.65f),
                                colors = ButtonDefaults.buttonColors().copy(
                                    containerColor = if (status == BookStatus.LEYENDO) Color(
                                        4,
                                        33,
                                        71,
                                        255
                                    ) else Color(57, 86, 125, 255)
                                )
                            ) {
                                Text("Leyendo")
                            }

                        }
                        Row {
                            Button(
                                onClick = {
                                    onStatusChange(book.id, BookStatus.TERMINADO)
                                    status = BookStatus.TERMINADO
                                },
                                modifier = Modifier.fillMaxWidth(0.65f),
                                colors = ButtonDefaults.buttonColors().copy(
                                    containerColor = if (status == BookStatus.TERMINADO) Color(
                                        4,
                                        33,
                                        71,
                                        255
                                    ) else Color(57, 86, 125, 255)
                                )
                            ) {
                                Text("Terminados")
                            }
                        }

                    }

                }
            }

        }
    }

}