package com.example.lurubooks.books

data class Books(
    val id: String,
    val title: String,
    val author: String,
    var status: BookStatus = BookStatus.NO_GUARDADO,
    var isFavorite: Boolean = false,
    val coverUrl: String? = null
)
enum class BookStatus {
    NO_GUARDADO, PENDIENTE, LEYENDO, TERMINADO
}
