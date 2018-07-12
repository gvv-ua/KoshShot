package ua.gvv.koshshot.data.entities

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

data class Action(
        var actionType: ActionType,
        val start: Point,
        val end: Point,
        var color: Int
) {
    fun isValid(): Boolean {
        return start.isValid() && end.isValid()
    }

        fun copy() = Action(actionType, start.copy(), end.copy(), color)

    private val paint = Paint().apply {
        color = this@Action.color
    }

    fun draw(canvas: Canvas) {
        when (actionType) {
            ActionType.Shape, ActionType.Rect -> drawShape(canvas)
            ActionType.Circle -> drawCircle(canvas)
        }
    }

    private fun drawShape(canvas: Canvas) {
        if (actionType == ActionType.Rect) {
            paint.strokeWidth = 5.toFloat()
            paint.style = Paint.Style.STROKE
        }
        canvas.drawRect(Rect(start.intX, start.intY, end.intX, end.intY), paint)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawOval(start.intX.toFloat(), start.intY.toFloat(), end.intX.toFloat(), end.intY.toFloat(), paint)
    }

}