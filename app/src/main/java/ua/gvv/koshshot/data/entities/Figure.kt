package ua.gvv.koshshot.data.entities

import androidx.annotation.DrawableRes

data class Figure (
        val type: ActionType,
        val name: String,
        @DrawableRes val image: Int
)