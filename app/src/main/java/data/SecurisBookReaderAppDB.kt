package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Books_Object

// USE A COMMA TO ADD ADDITIONAL DBS
// @Database(entities = [User::class, ^^^^^^^ ], version = 1, exportSchema = false)
@Database(entities = [User::class, Books_Object::class], version = 1, exportSchema = false)
abstract class SecurisBookReaderAppDB : RoomDatabase() {
        abstract fun SecurisBookReaderDao(): SecurisBookReaderDao

    //a companion object is similar to Java static declarations.
        //adding companion to the object declaration allows for adding
        // the "static" functionality to an object
        // used to create singleton object
        companion object {
            @Volatile
            private var INSTANCE: SecurisBookReaderAppDB? = null

            fun getDatabase(context: Context): SecurisBookReaderAppDB {
                //?: takes the right-hand value if the left-hand value is null (the elvis operator)
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SecurisBookReaderAppDB::class.java,
                        "user_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
}
