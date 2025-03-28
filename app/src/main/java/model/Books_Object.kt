package model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "books_table")
data class Books_Object(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String
)