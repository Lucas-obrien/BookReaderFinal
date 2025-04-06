package com.securis.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            val read = booksRepository.getReadCount()
            val started = booksRepository.getStartedCount()
            val notStarted = booksRepository.getNotStartedCount()

            _uiState.value = StatsUiState(
                readCount = read,
                startedCount = started,
                notStartedCount = notStarted
            )
        }
    }
}

data class StatsUiState(
    val readCount: Int = 0,
    val startedCount: Int = 0,
    val notStartedCount: Int = 0
)
