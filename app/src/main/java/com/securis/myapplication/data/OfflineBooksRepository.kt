package com.securis.myapplication.data


import kotlinx.coroutines.flow.Flow

// Holding onto this for potential functionality, ignore warning
class OfflineBooksRepository(private val bookDao: BookDao) : BooksRepository
{
    override fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllBooks()

    override fun getBookStream(id: Int): Flow<Book?> = bookDao.getBook(id)

    override suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    override suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)

    override suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    override fun getFirstThreeBooksStream(): Flow<List<Book>> = bookDao.getFirstThreeBooks()
    override fun searchBooksByTitle(query: String): Flow<List<Book>> = bookDao.searchBooksByTitle(
        title = query
    )

    override fun searchBooksByAuthor(query: String): Flow<List<Book>> = bookDao.searchBooksByAuthor(author = query)

    override fun searchBooksByGenre(query: String): Flow<List<Book>> = bookDao.searchBooksByGenre(genre = query)

    override fun searchBooksByMinRating(minRating: Float): Flow<List<Book>> = bookDao.searchBooksByMinRating(
        minRating
    )

    override suspend fun refreshBooksFromApi() {
//        TODO("Not yet implemented")
    }

    override fun getTopUnreadBooks(): Flow<List<Book>> {
        TODO("Not yet implemented")
    }

    override suspend fun getReadCount(): Int{
        TODO("Not yet implemented")
    }

    override suspend fun getStartedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getNotStartedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUnreadBooks(): List<Book> {
        TODO("Not yet implemented")
    }
}
