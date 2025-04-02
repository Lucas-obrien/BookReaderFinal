package com.securis.myapplication.data


import kotlinx.coroutines.flow.Flow

class OfflineBooksRepository(private val bookDao: BookDao) : BooksRepository
{
    override suspend fun addBooks() {
//        TODO("Not yet implemented")
    }

    override fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllBooks()

    override fun getBookStream(id: Int): Flow<Book?> = bookDao.getBook(id)

    override suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    override suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)

    override suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    override fun getFirstThreeBooksStream(): Flow<List<Book>> = bookDao.getFirstThreeBooks()

}
