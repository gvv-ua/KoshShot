package ua.gvv.koshshot.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_figure_dialog.*
import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.ActionType

class FigureDialogFragment : DialogFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }

    private val adapter: FigureListAdapter by lazy { FigureListAdapter(selectListener) }

    private val selectListener: FigureSelectListener = { figure ->
        listener?.onFigureSelect(figure.type)
        dismiss()
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_figure_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (rv_figures) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FigureDialogFragment.adapter
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) listener = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.submitList(viewModel.figures)
    }

    interface OnFragmentInteractionListener {
        fun onFigureSelect(type: ActionType)
    }

    companion object {
        @JvmStatic
        fun getInstance(): FigureDialogFragment = FigureDialogFragment()
    }
}