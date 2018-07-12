package ua.gvv.koshshot.ui.edit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import kotlinx.android.synthetic.main.activity_edit.*
import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.Action
import ua.gvv.koshshot.data.entities.ActionType
import ua.gvv.koshshot.data.entities.Point


class EditActivity : AppCompatActivity(), ColorPickerDialogListener, FigureDialogFragment.OnFragmentInteractionListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }

    private lateinit var originalBitmap: Bitmap
    private var currentAction: Action = Action(ActionType.Rect, Point(null, null), Point(null, null), 0)
    private var actionBarHeight = 0
    private var undoMenuItem: MenuItem? = null
    private var redoMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        actionBarHeight = getActionBarSize()

        if (intent.clipData != null && intent.clipData.itemCount > 0) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)

        menu?.findItem(R.id.action_edit_figure)?.setOnMenuItemClickListener {
            showFigureDialog()
            true
        }
        menu?.findItem(R.id.action_edit_color)?.setOnMenuItemClickListener {
            ColorPickerDialog.newBuilder().setColor(viewModel.currentColor).show(this@EditActivity)
            true
        }

        undoMenuItem = menu?.findItem(R.id.action_edit_undo)
        undoMenuItem?.setOnMenuItemClickListener {
            viewModel.undo()
            true
        }
        undoMenuItem?.isEnabled = false

        redoMenuItem = menu?.findItem(R.id.action_edit_redo)
        redoMenuItem?.setOnMenuItemClickListener {
            viewModel.redo()
            true
        }
        redoMenuItem?.isEnabled = false

        return true
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

    override fun onDialogDismissed(dialogId: Int) {}

    override fun onColorSelected(dialogId: Int, color: Int) {
        viewModel.currentColor = color
    }

    override fun onFigureSelect(type: ActionType) {
        viewModel.currentType = type
    }

    private fun initObserver() {
        viewModel.actions.observe(this, Observer {
            undoMenuItem?.isEnabled = it.isNotEmpty()
            iv_screenshot.setImageBitmap(viewModel.drawActions(originalBitmap))
        })
        viewModel.actionsUndo.observe(this, Observer {
            redoMenuItem?.isEnabled = it.isNotEmpty()
        })
    }


    private fun touchEnd() {
        if (currentAction.isValid()) {
            currentAction.color = viewModel.currentColor
            currentAction.actionType = viewModel.currentType
            viewModel.addAction(currentAction)
            Log.d(TAG, "${viewModel.actions}")
        }
    }

    private fun touchMove(x: Float?, y: Float?) {
        currentAction.end.x = x
        currentAction.end.y = y
    }

    private fun touchStart(x: Float?, y: Float?) {
        if (x != null && y != null) {
            currentAction.start.x = x
            currentAction.start.y = y - actionBarHeight
            currentAction.end.x = x
            currentAction.end.y = y - actionBarHeight
        }
    }

    private fun showFigureDialog() {
        val transaction = supportFragmentManager.beginTransaction()
        val prevDialog = supportFragmentManager.findFragmentByTag(TAG_FIGURE_DIALOG)
        if (prevDialog != null) transaction.remove(prevDialog)
        transaction.addToBackStack(null)
        FigureDialogFragment.getInstance().show(transaction, "dialog")
    }

    fun getActionBarSize(): Int {
        val styledAttributes = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarSize = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()
        return actionBarSize
    }

    companion object {
        private const val TAG = "EditActivity"
        private const val TAG_FIGURE_DIALOG = "figure_dialog"
    }
}
