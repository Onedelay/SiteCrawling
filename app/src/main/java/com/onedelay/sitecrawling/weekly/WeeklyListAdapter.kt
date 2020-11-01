package com.onedelay.sitecrawling.weekly

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.data.model.entity.WeeklyItem
import kotlinx.android.synthetic.main.viewholder_weekly.view.*


internal class WeeklyListAdapter : ListAdapter<WeeklyItem, WeeklyListAdapter.ViewHolder>(callback) {

    companion object {
        private val callback = object : DiffUtil.ItemCallback<WeeklyItem>() {
            override fun areItemsTheSame(oldItem: WeeklyItem, newItem: WeeklyItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: WeeklyItem, newItem: WeeklyItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var listener: ((item: WeeklyItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(parent, listener = { clickPosition ->
            listener?.invoke(getItem(clickPosition))
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: (item: WeeklyItem) -> Unit) {
        this.listener = listener
    }

    class ViewHolder constructor(
            parent: ViewGroup,
            private val listener: ((position: Int) -> Unit)?

    ) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_weekly, parent, false)) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.invoke(adapterPosition)
                }
            }
        }

        private val headlineTextView = itemView.tv_weekly_headline
        private val contentTextView = itemView.tv_weekly_content

        fun bind(item: WeeklyItem) {
            headlineTextView.text = item.headline
            contentTextView.text = item.contents
        }
    }

}