package model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Book (
    @StringRes val name: Int,
    @StringRes val review: Int,
    @DrawableRes val imageRes: Int
)