package com.securis.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import prepopulateBooksFromApi

class BooksRepositoryImpl(private val bookDao: BookDao) : BooksRepository {

    // Function to insert 10 books into the database
    override suspend fun addBooks() {
        val books = listOf(
            Book(id = 1, title = "1984", author = "George Orwell", blurb = "Its okay", genre = "Dystopian", ApiRating = 5),
            Book(id = 2, title = "Brave New World", author = "Aldous Huxley", blurb = "Its okay", genre = "Science Fiction", ApiRating = 5),
            Book(id = 3, title = "Fahrenheit 451", author = "Ray Bradbury", blurb = "Its okay", genre = "Dystopian", ApiRating = 5),
            Book(id = 4, title = "The Great Gatsby", author = "F. Scott Fitzgerald", blurb = "Its okay", genre = "Fiction", ApiRating = 5),
            Book(id = 5, title = "Moby Dick", author = "Herman Melville", blurb = "Its okay", genre = "Fiction", ApiRating = 5),
            Book(id = 6, title = "War and Peace", author = "Leo Tolstoy", blurb = "Its okay", genre = "Historical Fiction", ApiRating = 5),
            Book(id = 7, title = "The Catcher in the Rye", author = "J.D. Salinger", blurb = "Its okay", genre = "Historical Fiction", ApiRating = 5),
            Book(id = 8, title = "Pride and Prejudice", author = "Jane Austen", blurb = "Its okay", genre = "Fiction", ApiRating = 5),
            Book(id = 9, title = "The Hobbit", author = "J.R.R. Tolkien", blurb = "Its okay", genre = "Fantasy Fiction", ApiRating = 5),
            Book(id = 10, title = "The Lord of the Rings", author = "J.R.R. Tolkien", blurb = "Its okay", genre = "Fantasy Fiction", ApiRating = 5)
        )

        withContext(Dispatchers.IO) {
            bookDao.insertAll(books) // Insert all books into the database
        }
    }

    // Function to return a Flow of List<Book>
    override fun getAllBooksStream(): Flow<List<Book>> {
        return bookDao.getAllBooks() // This should return a Flow<List<Book>>
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    override fun getBookStream(id: Int): Flow<Book?> {
        return bookDao.getBook(id) // Assuming bookDao is a Room DAO
    }

    override suspend fun deleteBook(book: Book) {
        return bookDao.deleteBook(book)
    }


    // Function to insert a single book into the data source
    override suspend fun insertBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.insertBook(book) // Insert a single book
        }
    }

    override fun getFirstThreeBooksStream(): Flow<List<Book>> {
        return bookDao.getFirstThreeBooks()
    }

    override fun searchBooksByTitle(query: String): Flow<List<Book>> {
        return bookDao.searchBooksByTitle(query)
    }

    override fun searchBooksByAuthor(query: String): Flow<List<Book>> {
        return bookDao.searchBooksByAuthor(query)
    }

    override fun searchBooksByGenre(query: String): Flow<List<Book>> {
        return bookDao.searchBooksByGenre(query)
    }

    override fun searchBooksByMinRating(minRating: Float): Flow<List<Book>> {
        return bookDao.searchBooksByMinRating(minRating)
    }

    override suspend fun refreshBooksFromApi() {
        prepopulateBooksFromApi(bookDao)
    }

}
