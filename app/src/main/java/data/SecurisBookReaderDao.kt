package data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.old_book
import model.Books_Object

@Dao
interface SecurisBookReaderDao {
        //suspending function: a function that can be paused and resumed at a later time
        //suspending function: can execute a long running operation and wait for it to complete without blocking
        //suspending functions can only be invoked by another suspending function or within a coroutine
        //Use suspend:
        //1. When defining asynchronous functions.
        //2. For IO-bound operations (network, database, file).
        //3. To declare functions that can be paused and resumed.

        @Insert
        suspend fun insert(user: User)

        @Query("SELECT * FROM user_table WHERE id = :id")
        suspend fun getUserById(id: Int): User?

        @Query("DELETE FROM user_table")
        suspend fun deleteAllUsers()

        @Query("SELECT * FROM user_table ORDER BY "
                + "level ASC, "
                + "score DESC, "
                + "duration ASC, "
                + "username ASC")
        fun getAllUsers(): List<User>

        @Query("SELECT * FROM user_table ORDER BY "
                + "level ASC, "
                + "score DESC, "
                + "duration ASC, "
                + "username ASC")
        fun getAllUsersLiveData(): LiveData<List<User>>

        @Insert
        suspend fun insertBook(book: Books_Object)

        @Query("SELECT * FROM books_table WHERE id = :id")
        suspend fun getBookById(id: Int): Books_Object?

        @Query("DELETE FROM books_table")
        suspend fun deleteAllBooks()

        @Query("SELECT * FROM books_table ORDER BY "
                + "title ASC, "
                + "author DESC ")
        fun getAllBooks(): List<Books_Object>

        @Query("SELECT * FROM books_table ORDER BY "
                + "title ASC, "
                + "author DESC")
        fun getAllBooksLiveData(): LiveData<List<Books_Object>>


}


