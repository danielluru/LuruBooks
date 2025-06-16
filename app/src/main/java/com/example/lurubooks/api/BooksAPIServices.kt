package com.example.lurubooks.api

import retrofit2.Response
import retrofit2.http.*

data class OpenLibraryDoc(
    val key: String,
    val author_name: List<String>?,
    val title: String?,
    val ratings_count: Int?,
    val cover_i: Int?
)

data class TrendingDoc(
    val key: String,
    val author_name: List<String>?,
    val title: String?,
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

data class TrendingResponse(
    val works: List<TrendingDoc>
)

interface BooksAPIService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("fields") fields: String = "key,title,author_name,ratings_count,cover_i",
        @Query("lang") lang: String = "es",
        @Query("page") page: Int = 1
    ): Response<OpenLibraryResponse>

    @GET("trending/daily.json")
    suspend fun getTrendingBooks(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Response<TrendingResponse>
}

