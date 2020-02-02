package com.onedelay.sitecrawling.news.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.onedelay.sitecrawling.news.model.entity.NewsItem
import com.onedelay.sitecrawling.news.view.BaseOnClickListener
import com.onedelay.sitecrawling.news.view.viewholders.BaseViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.NewsViewHolder
import com.onedelay.sitecrawling.news.view.viewholders.TopNewsViewHolder

class NewsListAdapter(private val listener: BaseOnClickListener) : RecyclerView.Adapter<BaseViewHolder>() {
    private val topCount = 3

    companion object {
        private const val VIEW_TYPE_TOP_NEWS = 1
        private const val VIEW_TYPE_NEWS = 2
    }

    private val news = ArrayList<NewsItem>()

    fun setItems(items: List<NewsItem>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOP_NEWS -> TopNewsViewHolder.create(parent, listener)
            else -> NewsViewHolder.create(parent, listener)
        }
    }

    override fun getItemCount() = news.size

    override fun getItemViewType(position: Int): Int {
        return if (position < topCount) {
            VIEW_TYPE_TOP_NEWS
        } else {
            VIEW_TYPE_NEWS
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(news[position])
        holder.itemView.isLongClickable = true
    }
}