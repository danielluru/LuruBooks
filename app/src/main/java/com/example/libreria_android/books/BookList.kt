package com.example.libreria_android.books

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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    books: List<Books>,
    onStatusChange: (Int, BookStatus) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Column {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar libro...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(34, 46, 80, 255),
                unfocusedContainerColor = Color(34, 46, 80, 255),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        LazyColumn() {
            item {
                Text(
                    "Biblioteca",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            items(books) { book ->
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .shadow(6.dp)
                        .background(color = Color(34, 46, 80, 255), shape = RoundedCornerShape(10.dp))
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