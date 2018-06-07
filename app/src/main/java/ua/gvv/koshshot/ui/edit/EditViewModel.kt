package ua.gvv.koshshot.ui.edit

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.graphics.Canvas
import ua.gvv.koshshot.data.entities.Action

class EditViewModel : ViewModel() {

    val actions: MutableLiveData<MutableList<Action>> = MutableLiveData()

    init {
        actions.value = mutableListOf()
    }

    fun addAction(currentAction: Action) {
        actions.value?.add(currentAction.copy())
        actions.postValue(actions.value)
    }

    fun drawActions(screenshot: Bitmap): Bitmap {
        val actionList = actions.value
        val bitmap = screenshot.copy(Bitmap.Config.ARGB_8888, true)
        if (actionList != null) {
            val canvas = Canvas()
            canvas.setBitmap(bitmap)
            for(action in actionList) {
                action.draw(canvas)
                //canvas.drawRect(Rect(action.start.intX, action.start.intY, action.end.intX, action.end.intY), Paint())
            }
        }
        return bitmap
    }


}