package com.securis.myapplication.ui.network

import com.securis.myapplication.ui.network.*
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BooksResponse
}
