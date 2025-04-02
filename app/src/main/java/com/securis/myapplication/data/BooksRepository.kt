package com.securis.myapplication.data

import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    // Function to insert 10 books into the database
    suspend fun addBooks()

    // Function to get all books as a Flow
    fun getAllBooksStream(): Flow<List<Book>>

    // Function to get a single book by its ID as a Flow
    fun getBookStream(id: Int): Flow<Book?>

    // Function to delete a single book
    suspend fun deleteBook(book: Book)


    // Function to update a single book
    suspend fun updateBook(book: Book)

    // Function to insert a single book into the data source
    suspend fun insertBook(book: Book)

    fun getFirstThreeBooksStream(): Flow<List<Book>>

    fun searchBooksByTitle(query: String): Flow<List<Book>>
    fun searchBooksByAuthor(query: String): Flow<List<Book>>
    fun searchBooksByGenre(query: String): Flow<List<Book>>
    fun searchBooksByMinRating(minRating: Float): Flow<List<Book>>

}
