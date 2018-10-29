package com.onedelay.sitecrawling.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.viewholder_news.view.*

class NewsListAdapter : RecyclerView.Adapter<NewsViewHolder>() {
    private val news = ArrayList<NewsItem>()

    fun setItems(items: List<NewsItem>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder.create(parent)

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }
}

class NewsViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var item: NewsItem? = null

    companion object {
        fun create(parent: ViewGroup) = NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false))
    }

    fun bind(item: NewsItem) {
        this.item = item
        itemView.tv_ranking.text = item.rank.toString()
        itemView.tv_name.text = item.name
    }
}