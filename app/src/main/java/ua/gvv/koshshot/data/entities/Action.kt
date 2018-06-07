package ua.gvv.koshshot.data.entities

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

data class Action (
        var actionType: ActionType,
        val start: Point,
        val end: Point
) {
    fun isValid(): Boolean {
        return start.isValid() && end.isValid()
    }

    fun copy() = Action(actionType, start.copy(), end.copy())

    fun draw(canvas: Canvas) {
        when (actionType) {
            ActionType.Shape -> drawShape(canvas)
            ActionType.Circle-> drawCircle(canvas)
        }
    }

    private fun drawShape(canvas: Canvas) {
        canvas.drawRect(Rect(start.intX, start.intY, end.intX, end.intY), Paint())
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawOval(start.intX.toFloat(), start.intY.toFloat(), end.intX.toFloat(), end.intY.toFloat(), Paint())
    }

}