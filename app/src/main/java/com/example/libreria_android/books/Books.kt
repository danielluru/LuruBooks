package com.example.libreria_android.books

import androidx.compose.runtime.snapshots.SnapshotStateList


data class Books(
    val id: String,
    val title: String,
    val author: String,
    var status: BookStatus = BookStatus.NO_GUARDADO,
    var isFavorite: Boolean = false,
    val image: Int = 0, // Resource ID for the book cover image
    val coverUrl: String? = null // Optional field for cover image URL
)
enum class BookStatus {
    NO_GUARDADO, PENDIENTE, LEYENDO, TERMINADO
}

fun updateBookStatus(sampleBooks: SnapshotStateList<Books>, bookId: String, newStatus: BookStatus) {
    sampleBooks.find { it.id == bookId }?.status = newStatus
}

fun toggleFavorite(sampleBooks: SnapshotStateList<Books>, bookId: String) {
    sampleBooks.find { it.id == bookId }?.let {
        it.isFavorite = !it.isFavorite
    }
}