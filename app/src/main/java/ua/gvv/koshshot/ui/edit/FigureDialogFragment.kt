package ua.gvv.koshshot.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_figure_dialog.*
import ua.gvv.koshshot.R

class FigureDialogFragment : DialogFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }

    private val adapter: FigureListAdapter by lazy { FigureListAdapter() }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.submitList(viewModel.figures)
    }

    companion object {
        @JvmStatic
        fun getInstance(): FigureDialogFragment = FigureDialogFragment()
    }
}