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


    @Query("""
            SELECT * FROM book_database AS b
            WHERE b.read != 1
            ORDER BY userRating DESC
            LIMIT 3
            """)
    fun getFirstThreeBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE title LIKE :title")
    fun searchBooksByTitle(title: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE author LIKE :author")
    fun searchBooksByAuthor(author: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE genre LIKE :genre")
    fun searchBooksByGenre(genre: String): Flow<List<Book>>

    @Query("SELECT * FROM book_database WHERE apiRating >= :minRating")
    fun searchBooksByMinRating(minRating: Float): Flow<List<Book>>

    @Query("SELECT COUNT(*) FROM book_database WHERE title = :title AND author = :author")
    suspend fun countByTitleAndAuthor(title: String, author: String): Int

    @Query("""
        SELECT genre 
        FROM book_database 
        WHERE userRating IS NOT NULL 
        GROUP BY genre 
        ORDER BY AVG(userRating) DESC
    """)
    suspend fun getRatedGenres(): List<String>

    @Query("""
        SELECT DISTINCT genre 
        FROM book_database
    """)
    suspend fun getAllGenres(): List<String>

    @Query("""
        SELECT * 
        FROM book_database 
        WHERE genre = :genre AND read = 0 
        ORDER BY id ASC 
        LIMIT 5
    """)
    suspend fun getUnreadBooksByGenre(genre: String): List<Book>

    @Query("SELECT COUNT(*) FROM book_database WHERE read = 1")
    suspend fun getReadCount(): Int

    @Query("SELECT COUNT(*) FROM book_database WHERE started = 1 AND read = 0")
    suspend fun getStartedCount(): Int

    @Query("SELECT COUNT(*) FROM book_database WHERE started = 0 AND read = 0")
    suspend fun getNotStartedCount(): Int

    @Query("SELECT * FROM book_database WHERE read = 0")
    suspend fun getAllUnreadBooks(): List<Book>

}
