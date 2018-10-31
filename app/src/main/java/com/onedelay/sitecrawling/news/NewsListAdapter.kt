package com.onedelay.sitecrawling.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.viewholder_news.view.*

class NewsListAdapter(private val listener: OnNewsClickListener) : RecyclerView.Adapter<NewsViewHolder>() {
    private val news = ArrayList<NewsItem>()

    interface OnNewsClickListener {
        fun onNewsClick(data: NewsItem?)
    }

    fun setItems(items: List<NewsItem>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    fun removeAll() {
        news.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder.create(parent, listener)

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }
}

class NewsViewHolder private constructor(itemView: View, private val listener: NewsListAdapter.OnNewsClickListener) : RecyclerView.ViewHolder(itemView) {
    private var item: NewsItem? = null

    init {
        itemView.setOnClickListener {
            listener.onNewsClick(item)
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: NewsListAdapter.OnNewsClickListener) = NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false), listener)
    }

    fun bind(item: NewsItem) {
        this.item = item
        itemView.tv_ranking.text = item.rank.toString()
        itemView.tv_name.text = item.name
    }
}