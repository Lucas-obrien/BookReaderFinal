package com.securis.myapplication.data

import kotlinx.coroutines.flow.Flow

interface BooksRepository {

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

    // Function to get top books from top three user genres
    fun getFirstThreeBooksStream(): Flow<List<Book>>

    // Function to search by title
    fun searchBooksByTitle(query: String): Flow<List<Book>>

    // Function to search by Author
    fun searchBooksByAuthor(query: String): Flow<List<Book>>

    // Function to search by Genre
    fun searchBooksByGenre(query: String): Flow<List<Book>>

    // Function to search by Minimum rating
    fun searchBooksByMinRating(minRating: Float): Flow<List<Book>>

    // Function to refresh DB from API
    suspend fun refreshBooksFromApi()

    // Function to get the top recommended Unread books
    fun getTopUnreadBooks(): Flow<List<Book>>

    // Function to return total read books
    suspend fun getReadCount(): Int

    // Function to get total currently being read books
    suspend fun getStartedCount(): Int

    // Function to get all unread books
    suspend fun getNotStartedCount(): Int

    suspend fun getAllUnreadBooks(): List<Book>
}
