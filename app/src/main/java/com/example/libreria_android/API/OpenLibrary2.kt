package com.example.libreria_android.API

import retrofit2.Response
import retrofit2.http.*

data class OpenLibraryDoc(
    val author_name: List<String>?,
    val title: String?,
    val ratings_count: Int?,
    val cover_i: Int?
)

data class OpenLibraryResponse(
    val numFound: Int,
    val start: Int,
    val numFoundExact: Boolean,
    val num_found: Int,
    val documentation_url: String?,
    val q: String?,
    val offset: Int?,
    val docs: List<OpenLibraryDoc>
)


interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("sort") sort: String = "rating",
        @Query("fields") fields: String = "title,author_name,ratings_count"
    ): Response<OpenLibraryResponse>
}

interface OpenLibraryApiImage {
    @GET("b/id/")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("sort") sort: String = "rating",
        @Query("fields") fields: String = "title,author_name,ratings_count"
    ): Response<OpenLibraryResponse>
}