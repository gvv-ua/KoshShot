package ua.gvv.koshshot.data.repositories

import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.Figure

class FigureRepository {

    companion object {
        fun getFigures(): List<Figure> {
            return listOf(
                    Figure(1, "Rect", R.drawable.ic_rect),
                    Figure(2, "Fil Rect", R.drawable.ic_rect),
                    Figure(3, "Arrow", R.drawable.ic_arrow),
                    Figure(4, "Oval", R.drawable.ic_circle)
            )
        }
    }
}