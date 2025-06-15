package com.example.libreria_android.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import coil3.compose.AsyncImage
import com.example.libreria_android.R
import com.example.libreria_android.books.Books
import com.example.libreria_android.books.toggleFavorite
import com.example.libreria_android.viewModels.BookViewModel
import kotlinx.coroutines.launch

@Composable
fun UserFavouritesScreen(modifier: Modifier = Modifier, viewModel: BookViewModel) {
    val sampleBooks by viewModel.books.collectAsState()
    LazyColumn {
        item {
            Text(
                "Favoritos",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(sampleBooks.size) { index ->
            val book = sampleBooks[index]
            if (book.isFavorite) {
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
                    AsyncImage(
                        model = book.coverUrl ?: R.drawable.cienanos,
                        contentDescription = "Portada del libro",
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


                        val coroutineScope = rememberCoroutineScope()

                        var favorite by remember { mutableStateOf(book.isFavorite) }
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.toggleFavorite(book)
                                    favorite = !favorite
                                }
                            },
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = Color(210, 1, 112, 255),
                            )
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
    }
}