package com.securis.myapplication.ui.network

data class OpenLibraryResponse(
    val docs: List<OpenLibraryDoc>
)

data class OpenLibraryDoc(
    val title: String?,
    val author_name: List<String>?,
    val first_sentence: Any?, // Can be string or map
    val subject: List<String>?,
    val rating: Float? = null // Open Library doesn't give this, we'll default
)
