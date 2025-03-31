package com.securis.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BooksRepositoryImpl(private val bookDao: BookDao) : BooksRepository {

    // Function to insert 10 books into the database
    override suspend fun addBooks() {
        val books = listOf(
            Book(id = 1, title = "1984", author = "George Orwell"),
            Book(id = 2, title = "Brave New World", author = "Aldous Huxley"),
            Book(id = 3, title = "Fahrenheit 451", author = "Ray Bradbury"),
            Book(id = 4, title = "The Great Gatsby", author = "F. Scott Fitzgerald"),
            Book(id = 5, title = "Moby Dick", author = "Herman Melville"),
            Book(id = 6, title = "War and Peace", author = "Leo Tolstoy"),
            Book(id = 7, title = "The Catcher in the Rye", author = "J.D. Salinger"),
            Book(id = 8, title = "Pride and Prejudice", author = "Jane Austen"),
            Book(id = 9, title = "The Hobbit", author = "J.R.R. Tolkien"),
            Book(id = 10, title = "The Lord of the Rings", author = "J.R.R. Tolkien")
        )

        withContext(Dispatchers.IO) {
            bookDao.insertAll(books) // Insert all books into the database
        }
    }

    // Function to return a Flow of List<Book>
    override fun getAllBooksStream(): Flow<List<Book>> {
        return bookDao.getAllBooks() // This should return a Flow<List<Book>>
    }



    override fun getBookStream(id: Int): Flow<Book?> {
        return bookDao.getBook(id) // Assuming bookDao is a Room DAO
    }

    override suspend fun deleteBook(book: Book) {
//        TODO("Not yet implemented")
    }

    override suspend fun updateBook(book: Book) {
//        TODO("Not yet implemented")
    }

    // Function to insert a single book into the data source
    override suspend fun insertBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.insertBook(book) // Insert a single book
        }
    }
}
