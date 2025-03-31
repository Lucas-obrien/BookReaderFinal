import androidx.lifecycle.SavedStateHandle

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
)