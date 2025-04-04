package com.securis.myapplication.ui.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    // Function that increases no. of queries to Google API, Google API caps at 40 responses per req
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40,
        @Query("startIndex") startIndex: Int = 0
    ): BooksResponse
}
