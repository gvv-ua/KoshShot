package ua.gvv.koshshot.ui.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.gvv.koshshot.R
import ua.gvv.koshshot.data.entities.Figure

typealias FigureSelectListener = (Figure) -> Unit

class FigureListAdapter(val selectListener: FigureSelectListener) : ListAdapter<Figure, FigureListAdapter.ViewHolder>(difCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.figure_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val icon: AppCompatImageView = itemView.findViewById(R.id.iv_figure_icon)
        val name: AppCompatTextView = itemView.findViewById(R.id.tv_figure_name)

        fun bind(figure: Figure) {
            icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, figure.image))
            name.text = figure.name
            itemView.setOnClickListener { this@FigureListAdapter.selectListener(figure) }
        }
    }

    companion object {
        val difCallback = object : DiffUtil.ItemCallback<Figure>() {
            override fun areItemsTheSame(oldItem: Figure, newItem: Figure): Boolean {
                return oldItem.type == newItem.type
            }

            override fun areContentsTheSame(oldItem: Figure, newItem: Figure): Boolean {
                return oldItem == newItem
            }
        }
    }
}