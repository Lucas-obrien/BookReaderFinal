package com.securis.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel



class BookEditViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    var bookUiState by mutableStateOf(BookUiState())
        private set


}
