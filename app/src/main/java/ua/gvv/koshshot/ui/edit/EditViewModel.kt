package ua.gvv.koshshot.ui.edit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.gvv.koshshot.data.entities.Action
import ua.gvv.koshshot.data.entities.ActionType
import ua.gvv.koshshot.data.repositories.FigureRepository

class EditViewModel : ViewModel() {

    val actions: MutableLiveData<MutableList<Action>> = MutableLiveData()
    val actionsUndo: MutableLiveData<MutableList<Action>> = MutableLiveData()

    val figures = FigureRepository.getFigures()
    var currentColor = Color.rgb(0, 0, 0)
    var currentType = ActionType.Rect

    init {
        actions.value = mutableListOf()
        actionsUndo.value = mutableListOf()
    }

    fun addAction(currentAction: Action) {
        actions.value?.add(currentAction.copy())
        actions.postValue(actions.value)
        actionsUndo.value?.clear()
        actionsUndo.postValue(actionsUndo.value)
    }

    fun drawActions(screenshot: Bitmap): Bitmap {
        val actionList = actions.value
        val bitmap = screenshot.copy(Bitmap.Config.ARGB_8888, true)
        if (actionList != null) {
            val canvas = Canvas()
            canvas.setBitmap(bitmap)
            for(action in actionList) {
                action.draw(canvas)
            }
        }
        return bitmap
    }

    fun undo() {
        val list = actions.value
        if (list != null && list.isNotEmpty()) {
            val action = list.removeAt(list.size - 1)
            actionsUndo.value?.add(action)
        }
        actions.postValue(list)
        actionsUndo.postValue(actionsUndo.value)
    }

    fun redo() {
        val list = actionsUndo.value
        if (list != null && list.isNotEmpty()) {
            val action = list.removeAt(list.size - 1)
            actions.value?.add(action)
        }
        actions.postValue(actions.value)
        actionsUndo.postValue(list)
    }
}