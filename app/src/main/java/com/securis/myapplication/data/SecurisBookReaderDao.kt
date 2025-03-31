//package data
//
//import Book
//import android.app.Application
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface SecurisBookReaderDao {
//
////        @Insert
////        suspend fun insert(user: User)
////
////        @Query("SELECT * FROM user_table WHERE id = :id")
////        suspend fun getUserById(id: Int): User?
////
////        @Query("DELETE FROM user_table")
////        suspend fun deleteAllUsers()
////
////        @Query("SELECT * FROM user_table ORDER BY level ASC, score DESC, duration ASC, username ASC")
////        fun getAllUsers(): List<User>
////
////        @Query("SELECT * FROM user_table ORDER BY level ASC, score DESC, duration ASC, username ASC")
////        fun getAllUsersLiveData(): LiveData<List<User>>
////
////        @Insert
////        suspend fun insertBook(book: Book)
////
////        // Remove suspend modifier here as Flow is asynchronous
////        @Query("SELECT * FROM books_table WHERE id = :id")
////        fun getBookById(id: Int): Flow<Book?>
////
////        @Query("DELETE FROM books_table")
////        suspend fun deleteAllBooks()
////
////        @Delete
////        suspend fun deleteBook(book: Book)
////
////        // Remove suspend modifier here as Flow is asynchronous
////        @Query("SELECT * FROM books_table ORDER BY title ASC, author DESC")
////        fun getAllBooks(): Flow<List<Book>>
////
////        @Query("SELECT * FROM books_table ORDER BY title ASC, author DESC")
////        fun getAllBooksLiveData(): LiveData<List<Book>>
////
////        @Update
////        suspend fun update(book: Book)
//
//
//        @Dao
//        interface BookDao {
//                @Query("SELECT * FROM books")
//                fun getAllBooks(): LiveData<List<Book>>
//        }
//}
