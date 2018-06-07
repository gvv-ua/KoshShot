package ua.gvv.koshshot.ui.edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_edit.*
import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.Action
import ua.gvv.koshshot.data.entities.ActionType
import ua.gvv.koshshot.data.entities.Point

class EditActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }

    private lateinit var originalBitmap: Bitmap
    private var currentAction: Action = Action(ActionType.Circle, Point(null, null), Point(null, null))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        if (intent.clipData.itemCount > 0) {
            val item = intent.clipData.getItemAt(0)
            if (item != null) {
                originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, item.uri)
                val bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                Canvas().setBitmap(bitmap)
                iv_screenshot.setImageBitmap(bitmap)
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.actions.observe(this, Observer {
            iv_screenshot.setImageBitmap(viewModel.drawActions(originalBitmap))
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
            }
            MotionEvent.ACTION_UP -> {
                touchEnd()
            }
        }
        return true        
    }

    private fun touchEnd() {
        if (currentAction.isValid()) {
            viewModel.addAction(currentAction)

        }
    }

    private fun touchMove(x: Float?, y: Float?) {
        currentAction.end.x = x
        currentAction.end.y = y
    }

    private fun touchStart(x: Float?, y: Float?) {
        if (x != null && y != null) {
            currentAction.start.x = x
            currentAction.start.y = y
            currentAction.end.x = x
            currentAction.end.y = y
        }
    }


    companion object {
        private const val TAG = "EditActivity"
    }
}
