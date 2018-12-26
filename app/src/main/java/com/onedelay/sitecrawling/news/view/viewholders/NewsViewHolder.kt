package com.onedelay.sitecrawling.news.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.view.NewsListAdapter
import kotlinx.android.synthetic.main.viewholder_news.view.*

class NewsViewHolder private constructor(itemView: View, listener: NewsListAdapter.OnNewsClickListener) : BaseViewHolder(itemView, listener) {
    companion object {
        fun create(parent: ViewGroup, listener: NewsListAdapter.OnNewsClickListener) = NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false), listener)
    }

    override fun bind(item: Any) {
        super.item = item as NewsItem
        itemView.tv_ranking.text = item.rank.toString()
        itemView.tv_name.text = item.name
    }
}