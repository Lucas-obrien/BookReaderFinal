// OpenLibraryApi.kt
package com.securis.myapplication.ui.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("page") page: Int
    ): OpenLibraryResponse
}