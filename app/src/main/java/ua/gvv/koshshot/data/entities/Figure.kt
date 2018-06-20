package ua.gvv.koshshot.data.entities

import androidx.annotation.DrawableRes

data class Figure (
        val id: Int,
        val name: String,
        @DrawableRes val image: Int
)