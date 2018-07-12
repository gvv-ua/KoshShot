package ua.gvv.koshshot.data.repositories

import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.ActionType
import ua.gvv.koshshot.data.entities.Figure

class FigureRepository {

    companion object {
        fun getFigures(): List<Figure> {
            return listOf(
                    Figure(ActionType.Rect, "Rect", R.drawable.ic_rect),
                    Figure(ActionType.Shape, "Fil Rect", R.drawable.ic_rect),
                    Figure(ActionType.Arrow, "Arrow", R.drawable.ic_arrow),
                    Figure(ActionType.Circle, "Oval", R.drawable.ic_circle)
            )
        }
    }
}