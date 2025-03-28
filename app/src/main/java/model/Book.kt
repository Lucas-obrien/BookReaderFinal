package model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

data class old_book (
    @StringRes val name: Int,
    @StringRes val review: Int,
    @DrawableRes val imageRes: Int
)




