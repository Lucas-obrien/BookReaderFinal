package com.securis.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_database")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val review: String,
    val genre: String,
    val rating: Int
) {

}