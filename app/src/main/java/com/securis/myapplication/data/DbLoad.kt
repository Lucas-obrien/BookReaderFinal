package com.securis.myapplication.data

import com.securis.myapplication.ui.network.GoogleBooksApi
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun prepopulateBooksFromApi(bookDao: BookDao) = withContext(Dispatchers.IO) {
    try {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(GoogleBooksApi::class.java)
        val response = api.searchBooks("fiction") // or "fantasy", or anything you like

        val books = response.items?.mapNotNull { item ->
            val info = item.volumeInfo
            val title = info.title ?: return@mapNotNull null
            val author = info.authors?.joinToString(", ") ?: "Unknown"

            Book(
                title = title,
                author = author,
                review = info.description?.take(300) ?: "No review available.",
                genre = info.categories?.firstOrNull() ?: "Unknown",
                rating = (info.averageRating ?: 3.0).toInt()
            )
        } ?: emptyList()

        bookDao.insertAll(books)
        Log.d("Prepopulate", "Inserted ${books.size} books from API")
    } catch (e: Exception) {
        Log.e("Prepopulate", "Error fetching books: ${e.localizedMessage}")
    }
}
