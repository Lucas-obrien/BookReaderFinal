package com.securis.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class BooksRepositoryImpl(private val bookDao: BookDao) : BooksRepository {

    // Function to return a Flow of List<Book>
    override fun getAllBooksStream(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    override fun getBookStream(id: Int): Flow<Book?> {
        return bookDao.getBook(id)
    }

    override suspend fun deleteBook(book: Book) {
        return bookDao.deleteBook(book)
    }


    // Function to insert a single book into the data source
    override suspend fun insertBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.insertBook(book)
        }
    }

    override fun getFirstThreeBooksStream(): Flow<List<Book>> = flow {
        val maxGenres = 3
        val booksPerGenre = 1

        val ratedGenres = bookDao.getRatedGenres()
        val allGenres = bookDao.getAllGenres()
        val unratedGenres = allGenres.filterNot { it in ratedGenres }

        val shuffledGenres = (ratedGenres + unratedGenres).distinct().shuffled()

        val validGenres = mutableListOf<String>()
        for (genre in shuffledGenres) {
            val unread = bookDao.getUnreadBooksByGenre(genre)
            if (unread.isNotEmpty()) {
                validGenres.add(genre)
            }
            if (validGenres.size == maxGenres) break
        }

        if (validGenres.isNotEmpty()) {
            val books = validGenres.flatMap { genre ->
                bookDao.getUnreadBooksByGenre(genre).shuffled().take(booksPerGenre)
            }
            emit(books)
        } else {
            // No valid genres with unread books — fallback
            val allUnreadBooks = bookDao.getAllUnreadBooks()
            emit(allUnreadBooks.shuffled().take(3))
        }
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

    override fun getTopUnreadBooks(): Flow<List<Book>> = flow {
        val maxGenres = 3
        val booksPerGenre = 5

        val ratedGenres = bookDao.getRatedGenres()
        val allGenres = bookDao.getAllGenres()
        val unratedGenres = allGenres.filterNot { it in ratedGenres }

        // Shuffle all genres to allow random fallback options
        val allShuffledGenres = (ratedGenres + unratedGenres).distinct().shuffled()

        val validGenres = mutableListOf<String>()
        for (genre in allShuffledGenres) {
            val books = bookDao.getUnreadBooksByGenre(genre)
            if (books.isNotEmpty()) {
                validGenres.add(genre)
            }
            if (validGenres.size == maxGenres) break
        }

        val selectedBooks = validGenres.flatMap { genre ->
            bookDao.getUnreadBooksByGenre(genre).shuffled().take(booksPerGenre)
        }

        emit(selectedBooks)
    }


    override suspend fun getReadCount(): Int {
        return bookDao.getReadCount()
    }

    override suspend fun getStartedCount(): Int {
        return bookDao.getStartedCount()
    }

    override suspend fun getNotStartedCount(): Int {
        return bookDao.getNotStartedCount()
    }

    override suspend fun getAllUnreadBooks(): List<Book> {
        return bookDao.getAllUnreadBooks()
    }

}
