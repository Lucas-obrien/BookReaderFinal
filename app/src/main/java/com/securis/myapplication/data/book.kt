package com.securis.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_database")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val blurb: String,
    val genre: String,
    val apiRating: Int,
    val userRating: Int? = null,
    val apiReview: String? = null,
    val userReview: String? = null,
    val read: Boolean = false,
    val started: Boolean = false
) {

}

