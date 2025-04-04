package com.securis.myapplication.data


import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_database")
    fun getAllBooks(): Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<Book>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM book_database WHERE id=:id")
    fun getBook(id: Int): Flow<Book?>

    @Delete
    suspend fun deleteBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)


    @Query("SELECT * FROM book_database LIMIT 3")
    fun getFirstThreeBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE title LIKE :title")
    fun searchBooksByTitle(title: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE author LIKE :author")
    fun searchBooksByAuthor(author: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE genre LIKE :genre")
    fun searchBooksByGenre(genre: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE rating >= :minRating")
    fun searchBooksByMinRating(minRating: Float): Flow<List<Book>>

    @Query("SELECT COUNT(*) FROM book_database WHERE title = :title AND author = :author")
    suspend fun countByTitleAndAuthor(title: String, author: String): Int
}
